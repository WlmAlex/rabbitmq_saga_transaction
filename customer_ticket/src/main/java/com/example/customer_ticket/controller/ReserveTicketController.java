package com.example.customer_ticket.controller;

import com.example.customer_ticket.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class ReserveTicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/reserve/{userId}/{roomId}/{reverseSeatsNum}")
    public ResponseEntity<String> reserveTicket(@PathVariable("userId") int userId
            , @PathVariable("roomId") int roomId
            , @PathVariable("reverseSeatsNum") int reverseSeatsNum) {
        boolean result = ticketService.reserveTicket(userId, roomId, reverseSeatsNum);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("success");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
    }
}
