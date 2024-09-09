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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sm.card_app.bean.CardRequestBean;
import com.sm.card_app.bean.PageBean;
import com.sm.card_app.entity.CardRequest;
import com.sm.card_app.service.CardRequestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cardapproval")
@CrossOrigin(origins = "*")
public class CardRequestController {

	@Autowired
	private CardRequestService cardRequestService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody CardRequestBean cardApprovalBean) {
		log.info("Received request to save cardRequest with number: {}", cardApprovalBean);
		try {
			cardRequestService.save(cardApprovalBean);
			log.info("Successfully saved cardRequest with number: {}", cardApprovalBean);
			String jsonString = "{\"message\":\"Appied Credit Card Successfully\"}";
			return ResponseEntity.status(HttpStatus.CREATED).body(jsonString);

		} catch (Exception e) {
			log.error("Error occurred while saving {} cardRequest with number: {}", cardApprovalBean, e);
			throw e;
		}
	}

	@GetMapping("/getall/{page}/{size}/{status}")
	public ResponseEntity<PageBean<List<CardRequestBean>>> get(@PathVariable int page, @PathVariable int size,
			@PathVariable String status) {
		log.info("Retrieving all card request for page {} and size {} :", page, size);
		return ResponseEntity.status(HttpStatus.OK).body(cardRequestService.getAll(page, size, status));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CardRequestBean> get(@PathVariable int id) {
		log.info("Received request to retrieve cardRequest by Id: {}", id);
		try {
			CardRequestBean cardApproval = cardRequestService.getById(id);
			if (cardApproval != null) {
				log.info("Successfully retrieved cardRequest with Id: {}", id);
				return ResponseEntity.status(HttpStatus.OK).body(cardApproval);
			} else {
				log.warn("No cardApproval found with Id: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

		} catch (Exception e) {
			log.error("Error occurred while retrieving cardRequest with Id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody CardRequestBean cardApprovalBean,
			@RequestParam String statusType) {
		log.info("Received request to update cardRequest with id: {}", cardApprovalBean);
		try {
			cardRequestService.update(cardApprovalBean, statusType);
			log.info("Successfully updated cardRequest with id: {}", cardApprovalBean);
			String jsonString = "{\"message\":\"Updated cardRequest successfully\"}";
			return ResponseEntity.status(HttpStatus.OK).body(jsonString);

		} catch (Exception e) {
			log.error("Error occurred while updating cardRequest with id: {}", cardApprovalBean, e);
			String errorMessage = "{\"message\":\"Error updating cardRequest\"}";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@GetMapping("/get/card/requests")
	public ResponseEntity<List<CardRequest>> getCardRequest(@RequestParam String email) {
		log.info("Retrieving all card requests");
		List<CardRequest> cardRequests = cardRequestService.getCreditCardRequest(email);
		log.info("Fetched card requests based on logged in user successfully");
		return ResponseEntity.status(HttpStatus.OK).body(cardRequests);
	}

}
