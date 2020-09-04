package com.learn.rabbitmq_saga.saga_common.core;

import java.util.UUID;

public class Message {

    private String routeKey;
    private String id;
    private int command;
    private String content;
    private boolean isDone;

    private boolean isAsync;

    public Message() {
    }

    public Message(String content, int command, String routeKey) {
        this.id = genId();

        this.content = content;
        this.command = command;
        this.routeKey = routeKey;

        this.isAsync = false;
        this.isDone = false;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean async) {
        isAsync = async;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getId() {
        return id;
    }

    public String genId() {
        return String.valueOf(UUID.randomUUID());
    }

    public void setRollbackCommand(int command) {
        this.command = command + Command.MARGIN;
    }


    public static final class Command {
        private static final int MARGIN = 1000;
        public static final int BOOK_TICKET = MARGIN + 0;
        public static final int RESERVE_SEAT = MARGIN + 1;
        public static final int MAKE_PAYMENT = MARGIN + 2;

        public static final int BOOK_TICKET_ROLLBACK = BOOK_TICKET + MARGIN;
        public static final int RESERVE_SEAT_ROLLBACK = RESERVE_SEAT + MARGIN;
        public static final int MAKE_PAYMENT_ROLLBACK = MAKE_PAYMENT + MARGIN;
    }
}
