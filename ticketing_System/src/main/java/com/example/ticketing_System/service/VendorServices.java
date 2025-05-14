package com.example.ticketing_System.service;

import com.example.ticketing_System.dto.*;
import com.example.ticketing_System.entity.ReleaseTicket;
import com.example.ticketing_System.entity.User;
import com.example.ticketing_System.entity.VendorWiseEvents;
import com.example.ticketing_System.exception.ResourceNotFoundException;
import com.example.ticketing_System.mapper.userMapper;
import com.example.ticketing_System.repository.UserRepository;
import com.example.ticketing_System.repository.VendorWiseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class VendorServices  {
    private final AdminService adminService;
    private final SystemToggleDto systemToggleDto;
    private final TicketPoolDto ticketPoolDto;

    private final  ReleaseTicketDto releaseTicketDto;
    private final List<Thread> vendorThreads = new ArrayList<>();

    private final UserRepository userRepository;

    private  final VendorWiseRepo vendorWiseRepo;
    private  final  EventService eventService;

    @Autowired
    public VendorServices(AdminService adminService, SystemToggleDto systemToggleDto, TicketPoolDto ticketPoolDto, ReleaseTicketDto releaseTicketDto, UserRepository userRepository, VendorWiseRepo vendorWiseRepo, EventService eventService) {
        this.adminService = adminService;
        this.systemToggleDto = systemToggleDto;
        this.ticketPoolDto = ticketPoolDto;
        this.releaseTicketDto = releaseTicketDto;
        this.userRepository = userRepository;

        this.vendorWiseRepo = vendorWiseRepo;
        this.eventService = eventService;
    }


    public boolean addTicket(Long vendorId, TicketDto ticketDto) {
        final boolean[] ticketAddedSuccessfully = {false};

            Thread vendorThread = new Thread(()->{
                int maxTicketCapacity = adminService.getConfiguration().getMax_Ticket_Capacity();
                int ticketReleaseRate = 1000*(adminService.getConfiguration().getTicket_Release_Rate());
                int ticketQty = ticketDto.getQty();
                Long id=0L;
                while (!Thread.currentThread().isInterrupted()){

                    synchronized (ticketPoolDto){
                        try {

                            System.out.println("Current pool size: " + ticketPoolDto.getTicketPoolList().size());
                            System.out.println("Requested ticket quantity: " + ticketQty);
                            System.out.println("Max capacity: " + maxTicketCapacity);
                            System.out.println("Release Rate : " + ticketReleaseRate);

                            while (ticketPoolDto.getTicketPoolList().size()+ticketQty>maxTicketCapacity){
                                ticketPoolDto.wait();
                            }

                            for (int i = 0; i <ticketQty; i++) {
                                if (ticketPoolDto.getTicketPoolList().size()<maxTicketCapacity){
                                    ticketPoolDto.getTicketPoolList().add(ticketDto);
                                    System.out.println(ticketPoolDto.getTicketPoolList().size());
                                    ticketAddedSuccessfully[0]=true;
                                    Thread.sleep(ticketReleaseRate);
                                }else {
                                    break;
                                }
                            }
                            System.out.println(vendorId);
                            String vendorName = getVendor(vendorId).getUsername();
                            ReleaseTicket releaseTicket = new ReleaseTicket(vendorId,vendorName,ticketDto);
                            releaseTicketDto.getReleaseTicketList().add(releaseTicket);
                            for (int i = 0; i <releaseTicketDto.getReleaseTicketList().size(); i++) {
                                System.out.println(releaseTicketDto.getReleaseTicketList().get(i).getVendorId());
                                System.out.println(releaseTicketDto.getReleaseTicketList().get(i).getVendorName());
                                System.out.println(releaseTicketDto.getReleaseTicketList().get(i).getTicketDto().getTicketId());
                                System.out.println(releaseTicketDto.getReleaseTicketList().get(i).getTicketDto().getEventId());
                            }
                            System.out.println(releaseTicketDto.getReleaseTicketList());

                            for (TicketDto ticketDto1 : ticketPoolDto.getTicketPoolList()){
                                System.out.println(ticketDto1.getTicketId());
                                System.out.println(ticketDto1.getQty());
                                System.out.println(ticketDto1.getEventId());


                            }

                            EventDto eventDto = eventService.getEventbyId(ticketDto.getEventId());

                            VendorWiseEvents vendorWiseEvents= new VendorWiseEvents(
                                    id,
                                    ticketDto.getEventId(),
                                    eventDto.getEventName(),
                                    vendorId,
                                    vendorName
                            );

                            vendorWiseRepo.save(vendorWiseEvents);

                            System.out.println(ticketPoolDto.getTicketPoolList());
                            ticketPoolDto.notifyAll();

                            break;
                        } catch (InterruptedException e) {
                            System.out.println("Vendor"+ vendorId+ " Interrupted.");
                            Thread.currentThread().interrupt();
                        }
                    }

                }

            });
            vendorThread.start();
            vendorThreads.add(vendorThread);
            return ticketAddedSuccessfully[0];

    }


    public userDto getVendor(long id){
        User getUser=userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Vendor is  not exists with given id "));
        return userMapper.mapToUserDto(getUser);
    }


}
