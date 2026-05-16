package com.example.prod_app.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5224277549388565019L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}