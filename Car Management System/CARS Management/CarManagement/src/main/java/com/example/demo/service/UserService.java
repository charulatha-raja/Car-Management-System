package com.example.demo.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Logs.LogUtil;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@Service
public class UserService 
{
	
    private static final Logger log = LogUtil.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;
	
	public User addUserDetails(User user)
	{
		log.info("addUserDetails method starts :::");
		user= userRepository.save(user);
		log.info("addUserDetails method ends :::");
		return user;
	}

}
