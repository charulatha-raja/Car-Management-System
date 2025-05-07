package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController 
{
	@Autowired
	UserService userService;
	
	@PostMapping("/admin/createAdmin")
	public User addAdminDetails(@RequestBody User adminUser)
	{
		adminUser.setRole("Admin");
		return userService.addUserDetails(adminUser);
	}
	
	@PostMapping("/carOwner/createCarOwner")
	public User addOwnerDetails(@RequestBody User adminUser)
	{
		adminUser.setRole("Car Owner");
		return userService.addUserDetails(adminUser);
	}
	
	@PostMapping("/customer/createCustomer")
	public User addCustomerDetails(@RequestBody User adminUser)
	{
		adminUser.setRole("Customer");
		return userService.addUserDetails(adminUser);
	}

}
