package com.example.ticketing_System.repository;

import com.example.ticketing_System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByIdAndRole(Long aLong,String role);

    Optional<User>findByEmailAndPassword(String email, String password);


}
