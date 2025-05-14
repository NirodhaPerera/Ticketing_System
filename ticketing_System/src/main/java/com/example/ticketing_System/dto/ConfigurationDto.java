package com.example.ticketing_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ConfigurationDto {
    private Long id;
    private int total_Tickets;
    private int ticket_Release_Rate;
    private int customer_Retrieval_Rate;
    private int max_Ticket_Capacity;


}
