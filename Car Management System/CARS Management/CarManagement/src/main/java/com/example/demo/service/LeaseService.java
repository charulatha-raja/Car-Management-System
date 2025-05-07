package com.example.demo.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Logs.LogUtil;
import com.example.demo.model.CarDetails;
import com.example.demo.model.LeaseDetails;
import com.example.demo.model.User;
import com.example.demo.repo.CarDetailsRepo;
import com.example.demo.repo.LeaseRepository;
import com.example.demo.repo.UserRepository;


@Service
public class LeaseService
{
    private static final Logger log = LogUtil.getLogger(LeaseService.class);

@Autowired
LeaseRepository leaseRepository;

@Autowired
CarDetailsRepo carRepository;

	@Autowired
	UserRepository userRepository;
	
	public Object startLease(LeaseDetails leaseDetails) {
        log.info("startLease method starts");

        Long customerId = leaseDetails.getCustomerId();
        Long carId = leaseDetails.getCarId();

        Optional<User> userOpt = userRepository.findById(customerId);
        if (userOpt.isEmpty()) {
            String errorMsg = "Customer with ID " + customerId + " not found.";
            log.severe(errorMsg);
            return errorMsg;
        }

        User user = userOpt.get();
        if (!user.getRole().equalsIgnoreCase("Customer")) {
            String errorMsg = "User with ID " + customerId + " is not an end customer.";
            log.severe(errorMsg);
            return errorMsg;
        }

        List<LeaseDetails> activeLeases = leaseRepository.findByCustomerIdAndEndDateIsNull(customerId);
         if (!activeLeases.isEmpty() && activeLeases.size() >= 2) {
            String errorMsg = "Customer with ID " + customerId + " already has 2 active leases.";
            log.severe(errorMsg);
            return errorMsg;
        }

        Optional<CarDetails> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            String errorMsg = "Car with ID " + carId + " not found.";
            log.severe(errorMsg);
            return errorMsg;
        }

        CarDetails car = carOpt.get();
        if (!car.getStatus().equals("Ideal")) {
            String errorMsg = "Car with ID " + carId + " is not available for lease.";
            log.severe(errorMsg);
            return errorMsg;
        }

        leaseDetails.setStartDate(LocalDate.now());
        leaseDetails.setEndDate(null);
        car.setStatus("On Lease");

        carRepository.save(car);
        LeaseDetails savedLease = leaseRepository.save(leaseDetails);

        log.info("Lease started successfully for customerId=" + customerId + ", carId=" + carId);
        return savedLease;
    }
	
	
	
	

    public Object endLease(Long leaseId) {
        log.info("endLease method starts");

        Optional<LeaseDetails> leaseOpt = leaseRepository.findById(leaseId);
        if (leaseOpt.isEmpty()) {
            String errorMsg = "Lease with ID " + leaseId + " not found.";
            log.severe(errorMsg);
            return errorMsg;
        }

        LeaseDetails lease = leaseOpt.get();
        lease.setEndDate(LocalDate.now());

        Optional<CarDetails> carOpt = carRepository.findById(lease.getCarId());
        if (carOpt.isEmpty()) {
            String errorMsg = "Car with ID " + lease.getCarId() + " not found.";
            log.severe(errorMsg);
            return errorMsg;
        }

        CarDetails car = carOpt.get();
        car.setStatus("Ideal");

        carRepository.save(car);
        LeaseDetails updatedLease = leaseRepository.save(lease);

        log.info("Lease ended successfully for leaseId=" + leaseId);
        return updatedLease;
    }

    
    
    
    
    
    public Object getCustomerHistory(Long customerId) {
        log.info("getCustomerHistory method starts");

        Optional<User> userOpt = userRepository.findById(customerId);
        if (userOpt.isEmpty()) {
            String errorMsg = "Customer with ID " + customerId + " not found.";
            log.severe(errorMsg);
            return errorMsg;
        }

        User user = userOpt.get();
        if (!user.getRole().equalsIgnoreCase("Customer")) {
            String errorMsg = "User with ID " + customerId + " is not an end customer.";
            log.severe(errorMsg);
            return errorMsg;
        }

        List<LeaseDetails> history = leaseRepository.findByCustomerId(customerId);

        log.info("Lease history fetched successfully for customerId=" + customerId);
        return history;
    }


	

}
