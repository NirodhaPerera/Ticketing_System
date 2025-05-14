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
public class EventDto {
    private Long eventId;
    private String eventName;
    private String date;
    private String time;
    private String location;
    private  String image;


}
