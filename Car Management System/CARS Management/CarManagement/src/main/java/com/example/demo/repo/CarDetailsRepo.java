package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CarDetails;

public interface CarDetailsRepo extends JpaRepository<CarDetails, Long> {	
	
    List<CarDetails> findByOwnerId(Long ownerId);
    List<CarDetails> findByStatus(String status);
	
}
