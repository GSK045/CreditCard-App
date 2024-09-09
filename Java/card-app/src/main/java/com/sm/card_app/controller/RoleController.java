package com.sm.card_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.card_app.bean.RoleBean;
import com.sm.card_app.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody RoleBean roleBean) {
		log.info("Received request to save role with number: {}", roleBean);
		try {
			roleService.save(roleBean);
			log.info("Successfully saved role with number: {}", roleBean);
			String jsonString = "{\"message\":\"Saved role\"}";
			return ResponseEntity.status(HttpStatus.CREATED).body(jsonString);

		} catch (Exception e) {
			log.error("Error occurred while saving role with number: {}", roleBean, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Error saving role\"}");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleBean> get(@PathVariable int id) {
		log.info("Received request to retrieve role by Id: {}", id);
		try {
			RoleBean role = roleService.getById(id);
			if (role != null) {
				log.info("Successfully retrieved role with Id: {}", id);
				return ResponseEntity.status(HttpStatus.OK).body(role);
			} else {
				log.warn("No role found with Id: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

		} catch (Exception e) {
			log.error("Error occurred while retrieving role with Id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody RoleBean roleBean) {
		log.info("Received request to update role with id: {}", roleBean);
		try {
			roleService.update(roleBean);
			log.info("Successfully updated role with id: {}", roleBean);
			String jsonString = "{\"message\":\"Updated role successfully\"}";
			return ResponseEntity.status(HttpStatus.OK).body(jsonString);

		} catch (Exception e) {
			log.error("Error occurred while updating role with id: {}", roleBean, e);
			String errorMessage = "{\"message\":\"Error updating role\"}";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

}
