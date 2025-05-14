package com.example.ticketing_System.service;


import com.example.ticketing_System.dto.EventDto;
import com.example.ticketing_System.entity.Configuration;
import com.example.ticketing_System.entity.Event;
import com.example.ticketing_System.entity.PurchaseTicket;
import com.example.ticketing_System.entity.VendorWiseEvents;
import com.example.ticketing_System.exception.ResourceNotFoundException;
import com.example.ticketing_System.mapper.eventMapper;
import com.example.ticketing_System.repository.EventRepository;
import com.example.ticketing_System.repository.PurchaseTicketRepository;
import com.example.ticketing_System.repository.VendorWiseRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EventService {

    private  EventRepository eventRepository;

    private VendorWiseRepo vendorWiseRepo;

    private PurchaseTicketRepository purchaseTicketRepository;

    public EventService(EventRepository eventRepository, VendorWiseRepo vendorWiseRepo, PurchaseTicketRepository purchaseTicketRepository) {
        this.eventRepository = eventRepository;
        this.vendorWiseRepo = vendorWiseRepo;
        this.purchaseTicketRepository = purchaseTicketRepository;
    }

    public EventDto createEvent(Long vendorId, EventDto eventDto) {
        Event event = eventMapper.mapToEvent(eventDto);
        Event saveEvent = eventRepository.save(event);
        return eventMapper.mapToEventDto(saveEvent);
    }

    public List<EventDto> getEventList() {
        List<EventDto> eventDtoList = new ArrayList<>();
        for (Event event : eventRepository.findAll()) {
            eventDtoList.add(eventMapper.mapToEventDto(event));
        }

        return eventDtoList;
    }

    public EventDto getEventbyId(Long eventId) {
        Event event= eventRepository.findById(eventId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Configuration is  not exists with given id :" +eventId ));

        return eventMapper.mapToEventDto(event);
    }

    public boolean deleteEvent(Long eventId) {
        try {
            Event event= eventRepository.findById(eventId)
                    .orElseThrow(()->
                            new ResourceNotFoundException("Configuration is  not exists with given id :" +eventId ));
            eventRepository.delete(event);
            return true;
        }catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<EventDto> getEventListById(Long vendorId) {
        List<EventDto> eventDtoList = new ArrayList<>();
        VendorWiseEvents vendorWiseEvents = vendorWiseRepo.findById(vendorId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Configuration is  not exists with given id :" +vendorId ));


        for (Event event : eventRepository.findAllById(Collections.singleton(vendorWiseEvents.getEventId()))) {
            eventDtoList.add(eventMapper.mapToEventDto(event));
        }
        return eventDtoList;
    }

    public List<PurchaseTicket> getDetailsList() {
        List<PurchaseTicket> purchaseTicketList = new ArrayList<>();
        for (PurchaseTicket purchaseTicketD : purchaseTicketRepository.findAll()){
            purchaseTicketList.add(purchaseTicketD);
        }

        return purchaseTicketList;
    }
}
