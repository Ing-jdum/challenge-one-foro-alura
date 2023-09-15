package com.alura.infra.error;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handle404Error() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ValidationData>> handle400Error(MethodArgumentNotValidException e) {
		var errors = e.getFieldErrors().stream().map(ValidationData::new).toList();
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<String> handleBusinessValidations(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	private record ValidationData(String field, String error) {
		public ValidationData(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}

}