package com.learn.rabbitmq_saga.customer_account.repository;

import com.learn.rabbitmq_saga.customer_account.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {

    Account findUserById(int userId);

    void deposit(Account account);
}
