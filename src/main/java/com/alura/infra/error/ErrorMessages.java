package com.alura.infra.error;

public enum ErrorMessages {
	USER_NOT_FOUND("User not found"),
    COURSE_NOT_FOUND("Course not found"),
    TOPIC_EXISTS("Topic already exists");
    // Add more error messages here as needed

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
