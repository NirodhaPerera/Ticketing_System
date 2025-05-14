package com.example.ticketing_System.repository;

import com.example.ticketing_System.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<Configuration,Long> {
    Optional<Configuration> findTopByOrderByIdDesc();
}
