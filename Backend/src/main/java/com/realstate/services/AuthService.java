package com.realstate.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.User;
import com.realstate.exceptions.LoginException;
import com.realstate.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository; 
	
	public User login(String userName, String userPassword) throws LoginException {
		List<User> users = userRepository.findByNameAndPasswordAndActiveTrue(userName, userPassword);
		if (users.size() > 0) {
			return users.get(0);
		}
		throw new LoginException("Incorrect username or password");
	}
}
