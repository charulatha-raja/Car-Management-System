package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.LeaseDetails;
import com.example.demo.model.User;
import com.example.demo.service.LeaseService;

@RestController
@RequestMapping("/api/leases")
public class LeaseController
{
	
	 @Autowired
	    private LeaseService leaseService;

	    @PostMapping("/start")
	    public ResponseEntity<Object> startLease(@RequestBody LeaseDetails lease) {
	        try {
	            return ResponseEntity.ok(leaseService.startLease(lease));
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

	    @PutMapping("/end/{leaseId}")
	    public ResponseEntity<Object> endLease(@PathVariable Long leaseId) {
	        try {
	            return ResponseEntity.ok(leaseService.endLease(leaseId));
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

	    @GetMapping("/history/{customerId}")
	    public ResponseEntity<Object> getCustomerHistory(@PathVariable Long customerId) {
	        return ResponseEntity.ok(leaseService.getCustomerHistory(customerId));
	    }

}
