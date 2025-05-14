package com.example.ticketing_System.dto;


import com.example.ticketing_System.entity.ReleaseTicket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Getter
@Setter
@AllArgsConstructor
public class ReleaseTicketDto {
   private  List<ReleaseTicket> releaseTicketList;

    public ReleaseTicketDto() {
        this.releaseTicketList = new ArrayList<>();
    }
}
