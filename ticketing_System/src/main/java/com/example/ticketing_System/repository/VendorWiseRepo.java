package com.example.ticketing_System.repository;

import com.example.ticketing_System.entity.VendorWiseEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorWiseRepo extends JpaRepository<VendorWiseEvents,Long> {
}
