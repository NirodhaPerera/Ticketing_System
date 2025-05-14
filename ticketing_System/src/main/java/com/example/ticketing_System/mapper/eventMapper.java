package com.example.ticketing_System.mapper;

import com.example.ticketing_System.dto.ConfigurationDto;
import com.example.ticketing_System.dto.EventDto;
import com.example.ticketing_System.entity.Configuration;
import com.example.ticketing_System.entity.Event;

public class eventMapper {
    public static EventDto mapToEventDto(Event event){
        return new EventDto(
                event.getEventId(),
                event.getEventName(),
                event.getDate(),
                event.getTime(),
                event.getLocation(),
                event.getImage()

        );
    }

    public static Event mapToEvent(EventDto eventDto){
        return new Event(
                eventDto.getEventId(),
                eventDto.getEventName(),
                eventDto.getDate(),
                eventDto.getTime(),
                eventDto.getLocation(),
                eventDto.getImage()

        );
    }
}
