package com.sm.card_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.card_app.bean.PageBean;
import com.sm.card_app.bean.UserBean;
import com.sm.card_app.dto.LoginDto;
import com.sm.card_app.dto.UserDto;
import com.sm.card_app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody UserBean userBean) {
		log.info("Received request to save user with number: {}", userBean);
		try {
			userService.save(userBean);
			log.info("Successfully saved user : {}", userBean);
			String jsonString = "{\"message\":\"Saved user\"}";
			return ResponseEntity.status(HttpStatus.CREATED).body(jsonString);

		} catch (Exception e) {
			log.error("Error occurred while saving user with number: {}", userBean, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Error saving user\"}");
		}
	}

	@GetMapping("/getall/{page}/{size}")
	public ResponseEntity<PageBean<List<UserBean>>> get(@PathVariable int page, @PathVariable int size) {
		log.info("Retrieving all card request for page {} and size {} :", page, size);
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(page, size));
	}

	@GetMapping()
	public ResponseEntity<List<UserDto>> getUserDetails() {
		log.info("Retrieving all user details");
		List<UserDto> userDto = userService.getAllUserDetails();
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {
		log.info("login the user with user email : {} ", loginDto.getEmail());
		UserDto userDto = userService.loginUser(loginDto);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody UserBean userBean) {
		log.info("Received request to update user with id: {}", userBean);
		try {
			userService.update(userBean);
			log.info("Successfully updated user with id: {}", userBean);
			String jsonString = "{\"message\":\"Updated user successfully\"}";
			return ResponseEntity.status(HttpStatus.OK).body(jsonString);

		} catch (Exception e) {
			log.error("Error occurred while updating user with id: {}", userBean, e);
			String errorMessage = "{\"message\":\"Error updating user\"}";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

}
