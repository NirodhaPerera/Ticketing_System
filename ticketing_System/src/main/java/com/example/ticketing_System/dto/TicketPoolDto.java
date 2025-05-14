package com.example.ticketing_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Component
@AllArgsConstructor
public class TicketPoolDto {
    private List<TicketDto>ticketPoolList;

    public TicketPoolDto() {
        this.ticketPoolList = Collections.synchronizedList(new ArrayList<>());
    }

}
