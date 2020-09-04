package com.learn.rabbitmq_saga.orchestration.services;

import com.learn.rabbitmq_saga.saga_common.core.Message;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

@Service
public class OrchestrationService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private AsyncRabbitTemplate asyncRabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    class Process implements ListenableFutureCallback<Message> {

        public boolean status;
        public Message result;
        public Stack<Message> doneTasks;

        public Process(Stack<Message> doneTasks) {
            this.doneTasks = doneTasks;
            this.status = true;
            this.result = null;
        }

        @Override
        public void onFailure(Throwable throwable) {
            this.status = false;
        }

        @Override
        public void onSuccess(Message result) {
            this.result = result;

            if (result.isDone()) {
                doneTasks.push(result);
            } else {
                this.status = false;
            }
        }
    }

    /*@Scheduled(fixedDelay = 1000, initialDelay = 500)*/
    public Message sendSync(Message message) {

        String route = message.getRouteKey();
        System.err.println("Sending task: " + message.getCommand());

        return (Message) template.convertSendAndReceive(directExchange.getName(), route, message);

    }

    /*@Scheduled(fixedDelay = 1000L)*/
    public AsyncRabbitTemplate.RabbitConverterFuture<Message> sendAsync(Message message) {

        String route = message.getRouteKey();

        return asyncRabbitTemplate.convertSendAndReceive(directExchange.getName(), route, message);

    }

    public void tracker(Stack<Message> tasks, Message msg) {
        //transaction status
        boolean isFailed = false;

        /*Execute booking ticket transaction*/
        Stack<Message> doneTasks = new Stack<>();

        int totalTasks = tasks.size();
        List<Process> processTasks = new ArrayList<>();

        //start transaction execution thread
        while (totalTasks != doneTasks.size()) {
            if (tasks.isEmpty()) {
                if (processTasks.size() > 0) {
                    //continuously track the status of asynchronous tasks, stops until no processes
                    //let thread to track
                    Iterator<Process> iterator = processTasks.listIterator();
                    while (iterator.hasNext()) {
                        Process process = iterator.next();
                        if (process.result != null || !process.status) {
                            if (!process.status) {
                                isFailed = true;
                            }
                            iterator.remove();
                        }
                    }
                    continue;
                }
                //all tasks have been done, check transaction status, roll-back if needed
                if (isFailed) {
                    while (!doneTasks.isEmpty()) {
                        Message rollbackTask = doneTasks.pop();
                        rollbackTask.setRollbackCommand(rollbackTask.getCommand());
                        sendAsync(rollbackTask);
                    }
                    break;

                }
            } else {
                // sending command to other services
                Message task = tasks.pop();
                if (task.isAsync()) {
                    Process process = new Process(doneTasks);
                    processTasks.add(process);
                    sendAsync(task).addCallback(process);
                } else {
                    Message result = sendSync(task);
                    if (result.isDone()) {
                        doneTasks.push(task);
                    } else {
                        isFailed = true;
                        //part of the transaction has failed, there is no need to execute other part of current transactions.
                        break;
                    }
                }
            }
        }

        if (!isFailed) {
            msg.setDone(true);
        }

        if (isFailed) {
            while (!doneTasks.isEmpty()) {
                Message rollbackTask = doneTasks.pop();
                System.err.println("rollback task: " + rollbackTask.getCommand());
                rollbackTask.setRollbackCommand(rollbackTask.getCommand());
                sendAsync(rollbackTask);
            }
        }
    }
}