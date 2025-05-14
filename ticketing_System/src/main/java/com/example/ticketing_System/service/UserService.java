package com.example.ticketing_System.service;

import com.example.ticketing_System.dto.EventDto;
import com.example.ticketing_System.dto.LoginDto;
import com.example.ticketing_System.dto.userDto;
import com.example.ticketing_System.entity.Event;
import com.example.ticketing_System.entity.User;
import com.example.ticketing_System.exception.ResourceNotFoundException;
import com.example.ticketing_System.mapper.eventMapper;
import com.example.ticketing_System.mapper.userMapper;
import com.example.ticketing_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public userDto createUser(userDto userdto) {
        User user = userMapper.mapToUser(userdto);
        User savedCustomer =userRepository.save(user);
        return userMapper.mapToUserDto(savedCustomer);
    }

    public userDto updateUser(Long userId, userDto userdto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User is not exists with given id :"+ userId));
        user.setEmail(userdto.getEmail());
        user.setUsername(userdto.getUsername());
        user.setPassword(userdto.getPassword());
        user.setRole(userdto.getRole());

        User updatedUser =userRepository.save(user);
        return userMapper.mapToUserDto(updatedUser);

    }


    public userDto loginUser(String email, String password) {
        User user= userRepository.findByEmailAndPassword(email,password)
                .orElseThrow(()->new ResourceNotFoundException("User is not exists with given id :"+ email));
        return userMapper.mapToUserDto(user);
    }


    public userDto getUserbyId(Long userId) {
        User user= userRepository.findById(userId)
                .orElseThrow(()->
                        new ResourceNotFoundException("User is  not exists with given id :" +userId ));

        return userMapper.mapToUserDto(user);
    }
}
