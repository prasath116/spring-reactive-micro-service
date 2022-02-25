package com.prs.services.exceptionHandler;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

	private String message;
	private Throwable ex;

	public NotFoundException(String message, Throwable ex) {
		super(message, ex);
		this.message = message;
		this.ex = ex;
	}

	public NotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
