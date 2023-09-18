package com.alura.infra.error;

import java.util.List;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alura.infra.error.validations.ValidationError;

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
	
	@ExceptionHandler(ValidationError.class)
	public ResponseEntity<String> handleValidationsErrors(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<String> handleReferenceException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleReadException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	private record ValidationData(String field, String error) {
		public ValidationData(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}

}