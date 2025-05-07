package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Logs.LogUtil;
import com.example.demo.model.CarDetails;
import com.example.demo.model.User;
import com.example.demo.repo.CarDetailsRepo;
import com.example.demo.repo.UserRepository;

@Service
public class CarDetailsService
{
	@Autowired
    CarDetailsRepo carRepository;
	
	@Autowired
	UserRepository userRepository;
		
    private static final Logger log = LogUtil.getLogger(CarDetailsService.class);

	public Object registerCarDetails(CarDetails carOwner)
	{
		try {
		log.info("registerCarDetails method Starts");
		 Long ownerId = carOwner.getOwnerId();
	        
	        Optional<User> userData = userRepository.findById(ownerId);
	        if (userData.isEmpty()) {
	        	String errorMsg = "Registration failed: No user found with ID " + ownerId + ". Please provide a valid owner ID.";
	            log.severe(errorMsg);
	            return errorMsg;    
	        }

	        User user = userData.get();
	        if (!user.getRole().equalsIgnoreCase("Car Owner")) {
	        	String errorMsg = "Registration failed: User with ID " + ownerId + " is not authorized to register cars (role: " + user.getRole() + ").";
	            log.severe(errorMsg);
	            return errorMsg;
	        }
	        
	        // At initital Stage Status will be Ideal
	        carOwner.setStatus("Ideal");
	        log.info("registerCarDetails method ends");
	        return carRepository.save(carOwner);
		
	    } catch (Exception e) {
	        log.log(Level.SEVERE, "Unexpected error during car registration :::",e.getMessage() );
	    }
		return null;
		
	}
	
	
	
	public Object getCarDetailsByOwner(Long ownerId) {
		log.info("getCarDetailsByOwner method Starts");

        Optional<User> userOpt = userRepository.findById(ownerId);

        if (userOpt.isEmpty()) {
        	String errorMsg = "User with ID " + ownerId + " not found.";
            log.severe(errorMsg);
            return errorMsg;  
        }

        User user = userOpt.get();
        if (!user.getRole().equalsIgnoreCase("Car Owner")) {
        	String errorMsg = "User with ID " + ownerId + " is not a car owner.";
            log.severe(errorMsg);
            return errorMsg; 
        }
		log.info("carDetailsByOwner method Ends");
        return carRepository.findByOwnerId(ownerId);
    }

	 public List<CarDetails> getAllCars() {
	        log.info("getAllCars method started");
	        List<CarDetails> cars = carRepository.findAll();
	        log.info("getAllCars method ended, total cars: " + cars.size());
	        return cars;
	    }

	    public List<CarDetails> getCarsByStatus(String status) {
	        log.info("getCarsByStatus method started with status: " + status);

	        if (!status.equalsIgnoreCase("Ideal") &&
	            !status.equalsIgnoreCase("On Lease") &&
	            !status.equalsIgnoreCase("On Service")) {
	            String error = "Invalid status: " + status + ". Allowed values: Ideal, On Lease, On Service.";
	            log.severe(error);
	            throw new IllegalArgumentException(error);
	        }

	        List<CarDetails> cars = carRepository.findByStatus(status);
	        log.info("getCarsByStatus method ended, found: " + cars.size() + " cars");
	        return cars;
	    }
   

    
	

}
