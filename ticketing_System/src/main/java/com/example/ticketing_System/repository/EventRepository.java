package com.example.ticketing_System.repository;

import com.example.ticketing_System.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {

}
