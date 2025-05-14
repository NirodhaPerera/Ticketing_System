package com.example.ticketing_System.service;

import com.example.ticketing_System.dto.ConfigurationDto;

import java.util.List;

public interface AdminService {
    ConfigurationDto createConfiguration(ConfigurationDto configurationDto);

    ConfigurationDto getConfigurationById(Long configurationId);

    ConfigurationDto getConfiguration();

    ConfigurationDto UpdateConfiguration(Long configurationId, ConfigurationDto configurationDto);

    void toggleSystem(Boolean isActive);

    boolean toggleSystemState();

    List<ConfigurationDto> getConfigurationList();

    boolean deleteConfiguration(Long configurationId);
}


