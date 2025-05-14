package com.example.ticketing_System.entity;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "VendorWiseEvent")

public class VendorWiseEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventId;
    private String eventName;
    private Long vendorId;
    private String vendorName;

}
