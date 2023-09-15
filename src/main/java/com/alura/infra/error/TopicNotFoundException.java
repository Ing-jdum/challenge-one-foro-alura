package com.alura.infra.error;

public class TopicNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TopicNotFoundException(String message) {
		super(message);
	}
}
