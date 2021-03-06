package com.realstate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realstate.dto.LoginDto;
import com.realstate.dto.UserLoggedDto;
import com.realstate.exceptions.LoginException;
import com.realstate.services.AuthService;
import com.realstate.utils.Utils;

@RestController
@CrossOrigin(origins = "https://real-estate-adm-frontend.herokuapp.com", maxAge = 3600)
@RequestMapping("login")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
		UserLoggedDto user;
		
		if (loginDto.getUserName() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg("userName is null"));
		if (loginDto.getUserPassword() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg("userPassword is null"));
		
		try {
			user = authService.login(loginDto.getUserName(), loginDto.getUserPassword());
			return ResponseEntity.ok(Utils.objToJson(user));
		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utils.getExceptionResponseMsg(e));
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
}
