package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.LeaseDetails;

public interface LeaseRepository extends JpaRepository<LeaseDetails, Long> {

    List<LeaseDetails> findByCarId(Long carId);
	List<LeaseDetails> findByCustomerId(Long customerId);
    List<LeaseDetails> findByCustomerIdAndEndDateIsNull(Long customerId); 
}
