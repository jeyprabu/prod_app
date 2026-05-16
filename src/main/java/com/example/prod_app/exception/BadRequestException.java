package com.example.prod_app.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8782163811371754261L;

	public BadRequestException(String message) {
		super(message);
	}
	
}