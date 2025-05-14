package com.example.ticketing_System.entity;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "PurchaseTicket")
public class PurchaseTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchasedId;
    private String eventName;
    private Long eventId;
    private Long customerId;
    private String CustomerName;
    private Long vendorId;
    private String vendorName;

}
