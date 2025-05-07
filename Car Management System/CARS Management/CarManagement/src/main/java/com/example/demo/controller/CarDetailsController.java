package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CarDetails;
import com.example.demo.model.User;
import com.example.demo.service.CarDetailsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/owner") 

public class CarDetailsController 
{
	
	@Autowired
	CarDetailsService carDetailsService;
	
	
	@PostMapping("/registerCarDetails")
	public Object registerCarDetails(@RequestBody CarDetails carDetails)
	{
		return carDetailsService.registerCarDetails(carDetails);
	}
	
	
	    @GetMapping("/{ownerId}")
	    public ResponseEntity<Object> getCarsByOwner(@PathVariable Long ownerId) {
	        try {
	            Object cars = carDetailsService.getCarDetailsByOwner(ownerId);
	            if (cars==null) {
	                return ResponseEntity.ok("No cars found for owner with ID " + ownerId);
	            }
	            return ResponseEntity.ok(cars);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

	    @GetMapping("/carList")
	    public ResponseEntity<?> getAllCars() {
	        return ResponseEntity.ok(carDetailsService.getAllCars());
	    }

	    @GetMapping("/car/status")
	    public ResponseEntity<?> getCarsByStatus(@RequestParam String status) {
	            return ResponseEntity.ok(carDetailsService.getCarsByStatus(status));
	         
	    }
	

}
