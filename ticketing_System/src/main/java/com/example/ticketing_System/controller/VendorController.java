package com.example.ticketing_System.controller;


import com.example.ticketing_System.dto.EventDto;
import com.example.ticketing_System.dto.SystemToggleDto;
import com.example.ticketing_System.dto.TicketDto;
import com.example.ticketing_System.entity.PurchaseTicket;
import com.example.ticketing_System.service.EventService;
import com.example.ticketing_System.service.VendorServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    private VendorServices vendorServices;
    private EventService eventService;
    private  SystemToggleDto systemToggleDto;


    @PostMapping("{id}")
    public ResponseEntity<?> addTicket(@PathVariable("id") Long VendorId,@RequestBody TicketDto ticketDto){
        Map<String, String> response = new HashMap<>();

        if(systemToggleDto.isActive()){
            boolean addTicketSuccess =vendorServices.addTicket(VendorId,ticketDto);
            System.out.println(addTicketSuccess);
            if (addTicketSuccess){
                response.put("message","Ticket Cannot being Add to the Pool right now, Please Try again later.");

            }else{
                response.put("message", "Ticket Added Successfully...");


            }


        }else{
            response.put("message","System is Not Active in the moment");
        }
        return ResponseEntity.ok(response);





    }


    @PostMapping("saveEvent/{id}")
    public ResponseEntity<EventDto>saveEvent(@PathVariable("id")Long vendorId,@RequestBody EventDto eventDto){
        EventDto eventDto1 =eventService.createEvent(vendorId,eventDto);
        return new ResponseEntity<>(eventDto1, HttpStatus.CREATED);

    }


    @GetMapping("getEventList")
    public List<EventDto> getEventList(){
        return eventService.getEventList();
    }



    @DeleteMapping("deleteEvent/{id}")
    public ResponseEntity<?>deleteEvent(@PathVariable("id") Long eventId){
        Map<String, String> response = new HashMap<>();
        boolean success = eventService.deleteEvent(eventId);
        response.put("message", success? "Event Deleted Successfully":"Event Not Found");
        return ResponseEntity.ok(response);


        }

    @GetMapping("getEventListById/{id}")
    public List<EventDto> getEventList(@PathVariable("id")Long vendorId){
        return eventService.getEventListById(vendorId);
    }

    @GetMapping("getDetailsList")
    public List<PurchaseTicket> getDetailsList(){
        return eventService.getDetailsList();
    }





//    private String saveImage(byte[] decodedBytes) {
//        // Logic to save the decoded image and return the image URL or path
//
//        String imagePath = "/Users/nirodhaperera/Documents/CW/uploads/images/";  // Or a folder in your home directory
//
//          // Example
//        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
//            fos.write(decodedBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imagePath;
//    }

}
