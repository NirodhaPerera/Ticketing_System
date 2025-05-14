package com.example.ticketing_System.controller;

import com.example.ticketing_System.dto.ConfigurationDto;

import com.example.ticketing_System.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @PostMapping("saveConfiguration")
    public ResponseEntity<ConfigurationDto>createConfiguration(@RequestBody ConfigurationDto configurationDto){
        ConfigurationDto savedConfiguration = adminService.createConfiguration(configurationDto);
        return new ResponseEntity<>(savedConfiguration, HttpStatus.CREATED);
    }

    @GetMapping("getConfiguration/{id}")
    public  ResponseEntity<ConfigurationDto>getConfigurationById(@PathVariable("id") Long configurationId){
        ConfigurationDto configurationDto =adminService.getConfigurationById(configurationId);
        return ResponseEntity.ok(configurationDto);
    }

    @GetMapping("getConfigurationList")
    public List<ConfigurationDto> getConfigurationList(){
        return adminService.getConfigurationList();
    }

    @PutMapping("updateConfiguration/{id}")
    public  ResponseEntity<ConfigurationDto>updateConfigurationById(@PathVariable("id")  Long configurationId, @RequestBody ConfigurationDto configurationDto){
        ConfigurationDto updatedConfiguration = adminService.UpdateConfiguration(configurationId,configurationDto);
        return ResponseEntity.ok(updatedConfiguration);
    }

    @DeleteMapping("deleteConfiguration/{id}")
    public ResponseEntity<?>deleteConfiguration(@PathVariable("id") Long configurationId){
        Map<String, String> response = new HashMap<>();
        boolean success = adminService.deleteConfiguration(configurationId);
        response.put("message", success? "Configuration Deleted Successfully":"Configuration Not Found");
        return ResponseEntity.ok(response);


    }


    @PostMapping("toggleSystem")
    public ResponseEntity<?> toggleSystem(@RequestParam boolean isActive){
        System.out.println(isActive);
        Map<String, String> response = new HashMap<>();
        adminService.toggleSystem(isActive);
        response.put("message", isActive? "System is now active":"System is now inactive");
        return ResponseEntity.ok(response);


    }

    @GetMapping("toggleSystemState")
    public ResponseEntity<?>getToggleSystemState(){
        boolean systemActive = adminService.toggleSystemState();
        return  ResponseEntity.ok(systemActive);
    }


}
