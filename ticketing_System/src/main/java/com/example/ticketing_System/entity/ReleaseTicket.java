package com.example.ticketing_System.entity;

import com.example.ticketing_System.dto.TicketDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseTicket {
    private Long vendorId;
    private String vendorName;
    private TicketDto ticketDto;
}
