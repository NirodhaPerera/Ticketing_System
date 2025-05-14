package com.example.ticketing_System.controller;

import com.example.ticketing_System.dto.EventDto;

import com.example.ticketing_System.dto.SystemToggleDto;
import com.example.ticketing_System.service.CustomerService;
import com.example.ticketing_System.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerService customerService;
    private EventService eventService;
    private SystemToggleDto systemToggleDto;

    @PostMapping("purchaseTicket/{id}")
    public ResponseEntity<?>purchaseTicket(@PathVariable("id")Long customerId,@RequestParam("eventId") Long eventId, @RequestParam("ticketCount")int ticketCount){
        Map<String, String> response = new HashMap<>();
        if (systemToggleDto.isActive()){
            boolean purchaseTicketSuccess = customerService.purchaseTicket(customerId,eventId,ticketCount);
            if (purchaseTicketSuccess){
                response.put("message", "Ticket Purchased Successfully...");
            }else{
                response.put("message","Ticket Cannot Purchase right now, Please Try again later.");
            }

        }else{
            response.put("message","System is Not Active in the moment");
        }

        return ResponseEntity.ok(response);
    }



    @GetMapping("getEventList")
    public List<EventDto> getEventList(){
        return eventService.getEventList();
    }


    @GetMapping("getEventList/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable("id") Long eventId){
       EventDto eventDto= eventService.getEventbyId(eventId);
       return ResponseEntity.ok(eventDto);
    }


}
