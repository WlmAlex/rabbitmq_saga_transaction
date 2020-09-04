package com.example.customer_ticket.dao;

import com.example.customer_ticket.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketRepository {

    void save(Ticket ticket);

    Ticket findTicketById(int ticketId);
}