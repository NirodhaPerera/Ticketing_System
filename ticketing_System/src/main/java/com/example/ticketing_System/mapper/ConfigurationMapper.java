package com.example.ticketing_System.mapper;

import com.example.ticketing_System.dto.ConfigurationDto;
import com.example.ticketing_System.entity.Configuration;

public class ConfigurationMapper {

    public static ConfigurationDto mapToConfigurationDto(Configuration configuration){
        return new ConfigurationDto(
                configuration.getId(),
                configuration.getTotalTickets(),
                configuration.getTicketReleaseRate(),
                configuration.getCustomerRetrievalRate(),
                configuration.getMaxTicketCapacity()
        );
    }

    public static Configuration mapToConfiguration(ConfigurationDto configurationDto){
        return new Configuration(
                configurationDto.getId(),
                configurationDto.getTotal_Tickets(),
                configurationDto.getTicket_Release_Rate(),
                configurationDto.getCustomer_Retrieval_Rate(),
                configurationDto.getMax_Ticket_Capacity()
        );
    }
}
