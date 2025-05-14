package com.example.ticketing_System.service.impl;

import com.example.ticketing_System.dto.ConfigurationDto;
import com.example.ticketing_System.dto.SystemToggleDto;
import com.example.ticketing_System.entity.Configuration;
import com.example.ticketing_System.exception.ResourceNotFoundException;
import com.example.ticketing_System.mapper.ConfigurationMapper;
import com.example.ticketing_System.repository.ConfigurationRepository;
import com.example.ticketing_System.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private ConfigurationRepository configurationRepository;

    private SystemToggleDto systemToggleDto;

    @Override
    public ConfigurationDto createConfiguration(ConfigurationDto configurationDto) {
        Configuration configuration = ConfigurationMapper.mapToConfiguration(configurationDto);
        Configuration savedConfiguration = configurationRepository.save(configuration);

        return ConfigurationMapper.mapToConfigurationDto(savedConfiguration);

    }

    @Override
    public ConfigurationDto getConfigurationById(Long configurationId) {
        Configuration configuration= configurationRepository.findById(configurationId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Configuration is  not exists with given id :" +configurationId ));

        return ConfigurationMapper.mapToConfigurationDto(configuration);
    }

    @Override
    public ConfigurationDto getConfiguration() {
        Configuration configuration = configurationRepository.findTopByOrderByIdDesc().orElseThrow(()->
                new ResourceNotFoundException("Configuration is  not exists with given id "));
        return ConfigurationMapper.mapToConfigurationDto(configuration);
    }


    @Override
    public ConfigurationDto UpdateConfiguration(Long configurationId, ConfigurationDto updateConfigurationDto) {
        Configuration configuration= configurationRepository.findById(configurationId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Configuration is not exists with given id :"+ configurationId));

        configuration.setTotalTickets(updateConfigurationDto.getTotal_Tickets());
        configuration.setTicketReleaseRate(updateConfigurationDto.getTicket_Release_Rate());
        configuration.setCustomerRetrievalRate(updateConfigurationDto.getCustomer_Retrieval_Rate());
        configuration.setMaxTicketCapacity(updateConfigurationDto.getMax_Ticket_Capacity());

        Configuration updatedConfiguration = configurationRepository.save(configuration);

        return ConfigurationMapper.mapToConfigurationDto(updatedConfiguration);
    }


    public void toggleSystem(Boolean isActive) {
        systemToggleDto.setActive(isActive);

    }

    @Override
    public boolean toggleSystemState() {
        return systemToggleDto.isActive();

    }

    @Override
    public List<ConfigurationDto> getConfigurationList() {
        List<ConfigurationDto> configurationDtoList = new ArrayList<>();
       for (Configuration configuration : configurationRepository.findAll()) {
            configurationDtoList.add(ConfigurationMapper.mapToConfigurationDto(configuration));
        }

        return configurationDtoList;
    }

    @Override
    public boolean deleteConfiguration(Long configurationId) {
        try {
            Configuration configuration= configurationRepository.findById(configurationId)
                    .orElseThrow(()->
                            new ResourceNotFoundException("Configuration is  not exists with given id :" +configurationId ));
                configurationRepository.delete(configuration);
                return true;
            }catch (ResourceNotFoundException e){
                System.out.println(e.getMessage());
                return false;
            }


    }
}
