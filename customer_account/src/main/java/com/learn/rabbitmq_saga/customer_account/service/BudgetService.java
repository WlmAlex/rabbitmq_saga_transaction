package com.learn.rabbitmq_saga.customer_account.service;

import com.learn.rabbitmq_saga.customer_account.entity.Account;
import com.learn.rabbitmq_saga.customer_account.exception.InsufficientBudgetException;
import com.learn.rabbitmq_saga.customer_account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class BudgetService {

    @Autowired
    private AccountRepository repository;

    private boolean hasEnoughBudget(int userId, double balance) throws AccountNotFoundException {

        Account account = repository.findUserById(userId);

        if (account == null) {
            throw new AccountNotFoundException("user doesn't exist with id: " + userId);
        }

        return account.getDeposit() >= balance;

    }

    @Transactional
    public void withDrawn(int userId, double balance) throws AccountNotFoundException, InsufficientBudgetException {
        if (!hasEnoughBudget(userId, balance)) {
            throw new InsufficientBudgetException("balance insufficient");
        }

        //make payment here
        Account account = repository.findUserById(userId);
        double subtraction = account.getDeposit() - balance;
        account.setDeposit(subtraction);
        repository.deposit(account);
    }

    public void deposit(int userId, double balance) throws AccountNotFoundException {
        //make payment here
        Account account = repository.findUserById(userId);

        if (account == null) {
            throw new AccountNotFoundException("user doesn't exist with id: " + userId);
        }
        double subtraction = account.getDeposit() + balance;
        account.setDeposit(subtraction);
        repository.deposit(account);
    }

}