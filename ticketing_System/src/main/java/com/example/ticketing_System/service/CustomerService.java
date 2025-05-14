package com.example.ticketing_System.service;

import com.example.ticketing_System.dto.*;
import com.example.ticketing_System.entity.PurchaseTicket;
import com.example.ticketing_System.entity.ReleaseTicket;
import com.example.ticketing_System.entity.User;
import com.example.ticketing_System.entity.VendorWiseEvents;
import com.example.ticketing_System.exception.ResourceNotFoundException;
import com.example.ticketing_System.mapper.userMapper;
import com.example.ticketing_System.repository.PurchaseTicketRepository;
import com.example.ticketing_System.repository.UserRepository;
import com.example.ticketing_System.repository.VendorWiseRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final AdminService adminService;

    private final TicketPoolDto ticketPoolDto;
    

    private final ReleaseTicketDto releaseTicketDto;
    private final List<Thread> customerThreads = new ArrayList<>();
    
    private  final EventService eventService;
    private  final UserService userService;

    private  final PurchaseTicketRepository purchaseTicketRepository;

    private final VendorWiseRepo vendorWiseRepo;



    public CustomerService(AdminService adminService, TicketPoolDto ticketPoolDto, ReleaseTicketDto releaseTicketDto, EventService eventService, UserService userService, PurchaseTicketRepository purchaseTicketRepository, VendorWiseRepo vendorWiseRepo) {
        this.adminService = adminService;
        this.ticketPoolDto = ticketPoolDto;
        this.releaseTicketDto = releaseTicketDto;
        this.eventService = eventService;
        this.userService = userService;
        this.purchaseTicketRepository = purchaseTicketRepository;
        this.vendorWiseRepo = vendorWiseRepo;
    }

    @Transactional
    public boolean purchaseTicket(Long customerId,Long eventId, int ticketCount) {
        final boolean[] ticketPurchaseSuccessfully = {false};
        System.out.println("hello");

        System.out.println(eventId);
            Thread customerThread = new Thread(()->{
                int maxTicketCapacity = adminService.getConfiguration().getMax_Ticket_Capacity();
                int customerRetrievalRate = 1000*(adminService.getConfiguration().getCustomer_Retrieval_Rate());
                int removeTickets =0;
                Long purchasedId = 0L;
                int totalTicket= adminService.getConfiguration().getTotal_Tickets();

                System.out.println("sss"+ eventId);

                EventDto eventDto= eventService.getEventbyId(eventId);
                userDto userdto=userService.getUserbyId(eventId);

                VendorWiseEvents vendorWiseEvents= getById(eventId);

                while (!Thread.currentThread().isInterrupted()){
                    System.out.println("swd");
                    synchronized (ticketPoolDto){
                        try {
                            System.out.println("huuhgu");


                            System.out.println("pool size:"+ticketPoolDto.getTicketPoolList().size());
                            while (ticketPoolDto.getTicketPoolList().size()<ticketCount){
                                System.out.println(" Wait Until a Ticket Becomes Available");
                                ticketPoolDto.wait();
                            }

                            for (int i = 0; i <ticketPoolDto.getTicketPoolList().size()&& removeTickets< ticketCount ; i++) {
                                System.out.println("meka run wenwd");
                                    if (ticketPoolDto.getTicketPoolList().get(i).getEventId().equals(eventId)){

                                        System.out.println("delete wenwd");
                                        ticketPoolDto.getTicketPoolList().remove(i);
                                        removeTickets++;
                                        i--;
                                        ticketPurchaseSuccessfully[0]=true;
                                        totalTicket--;
                                        System.out.println("mkth wada da");
                                        Thread.sleep(2000);
                                    }
                            }

                            System.out.println(removeTickets);
                            /*System.out.println(releaseTicketDto.getReleaseTicketList());
                            for (TicketDto ticketDto1 : ticketPoolDto.getTicketPoolList()){
                                System.out.println(ticketDto1.getTicketId());
                                System.out.println(ticketDto1.getQty());
                                System.out.println(ticketDto1.getEventId());
                            }*/
                            ConfigurationDto configurationDto =new ConfigurationDto(
                                    adminService.getConfiguration().getId(),
                                    totalTicket,
                                    adminService.getConfiguration().getTicket_Release_Rate(),
                                    adminService.getConfiguration().getCustomer_Retrieval_Rate(),
                                    maxTicketCapacity
                            );


                            adminService.UpdateConfiguration(adminService.getConfiguration().getId(),configurationDto);

                            PurchaseTicket purchaseTicket= new PurchaseTicket(
                                    purchasedId,
                                    eventDto.getEventName(),
                                    eventId,
                                    customerId,
                                    userdto.getUsername(),
                                    vendorWiseEvents.getVendorId(),
                                    vendorWiseEvents.getVendorName()
                            );
                            System.out.println("hihihih");
                            purchaseTicketRepository.save(purchaseTicket);
                            System.out.println("ayyoooo");
                            ticketPoolDto.notifyAll();
                            break;
                        } catch (InterruptedException e) {
                            System.out.println("Customer"+ " Interrupted.");
                            Thread.currentThread().interrupt();
                        }
                    }

                }

            });
            customerThread.start();
            customerThreads.add(customerThread);
            return ticketPurchaseSuccessfully[0];


    }

    public VendorWiseEvents getById(Long eventId) {
        VendorWiseEvents vendorWiseEvents = vendorWiseRepo.findById(eventId)
                .orElseThrow(()->
                        new ResourceNotFoundException("User is  not exists with given id :" +eventId ));

        return vendorWiseEvents;
    }



}
