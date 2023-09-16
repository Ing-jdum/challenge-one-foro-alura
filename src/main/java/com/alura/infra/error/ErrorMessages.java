package com.alura.infra.error;

public enum ErrorMessages {
	USER_NOT_FOUND("User not found"),
	USER_EXISTS("Username already in use"),
	EMAIL_EXISTS("email already in use"),
    COURSE_NOT_FOUND("Course not found"),
    TOPIC_EXISTS("Topic already exists"),
    TOPIC_NOT_FOUND("The topic with the specified id was not found"),
    RESPONSE_NOT_FOUND("Response not found"),
	RESPONSE_EXISTS("Response already exists");
    // Add more error messages here as needed

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
