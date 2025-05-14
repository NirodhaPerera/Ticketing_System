package com.example.ticketing_System.repository;

import com.example.ticketing_System.entity.PurchaseTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseTicketRepository extends JpaRepository<PurchaseTicket,Long> {

}
