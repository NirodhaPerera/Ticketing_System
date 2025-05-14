package com.example.ticketing_System.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configurations")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "total_Tickets")
    private int totalTickets;
    @Column(name = "ticket_Release_Rate")
    private int ticketReleaseRate;
    @Column(name = "customer_Retrieval_Rate")
    private int customerRetrievalRate;
    @Column(name = "max_Ticket_Capacity")
    private int maxTicketCapacity;

}


