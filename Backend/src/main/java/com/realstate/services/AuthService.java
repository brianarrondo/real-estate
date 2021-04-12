package com.realstate.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.dto.UserLoggedDto;
import com.realstate.entities.User;
import com.realstate.exceptions.LoginException;
import com.realstate.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository; 
	@Autowired
	private JwtService jwtService;
	
	public UserLoggedDto login(String userName, String userPassword) throws LoginException {
		List<User> users = userRepository.findByNameAndPasswordAndActiveTrue(userName, userPassword);
		if (users.size() > 0) {
			return new UserLoggedDto(users.get(0), jwtService.getJWTToken(userName));
		}
		throw new LoginException("Incorrect username or password");
	}
}
