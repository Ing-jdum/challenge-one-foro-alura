package com.alura.infra.error.validations;

public class ValidationError extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public ValidationError(String message) {
        super(message);
    }
}
