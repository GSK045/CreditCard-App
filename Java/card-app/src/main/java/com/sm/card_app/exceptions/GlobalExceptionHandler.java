package com.sm.card_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CardRequestAlreadyExistsException.class)
	public ResponseEntity<String> handleCardRequestAlreadyExistsException(CardRequestAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"" + ex.getMessage() + "\"}");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("{\"message\":\"" + ex.getMessage() + "\"}");
	}
}
