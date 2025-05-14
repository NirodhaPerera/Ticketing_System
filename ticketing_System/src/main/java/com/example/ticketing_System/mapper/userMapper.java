package com.example.ticketing_System.mapper;

import com.example.ticketing_System.dto.userDto;
import com.example.ticketing_System.entity.User;

public class userMapper {

    public static userDto mapToUserDto(User user){
        return new userDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }

    public  static User mapToUser(userDto userDto){
        return new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }
}
