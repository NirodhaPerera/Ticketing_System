package com.example.ticketing_System.controller;

import com.example.ticketing_System.dto.userDto;
import com.example.ticketing_System.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @PostMapping("signUp")
    public ResponseEntity<userDto> createUser(@RequestBody userDto userdto){
        userDto saveUser = userService.createUser(userdto);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }


    @PostMapping("login/{email}&{password}")
    public ResponseEntity<userDto> loginUser(@PathVariable String email,@PathVariable String password){
        userDto saveUser = userService.loginUser(email,password);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }





}
