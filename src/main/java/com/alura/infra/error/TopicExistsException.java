package com.alura.infra.error;

public class TopicExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TopicExistsException(String message) {
		super(message);
	}
}
