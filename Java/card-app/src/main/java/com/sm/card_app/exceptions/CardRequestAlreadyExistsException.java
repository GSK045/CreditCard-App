package com.sm.card_app.exceptions;

public class CardRequestAlreadyExistsException extends RuntimeException {
	public CardRequestAlreadyExistsException(String message) {
        super(message);
    }

}
