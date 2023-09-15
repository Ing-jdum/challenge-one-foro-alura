package com.alura.infra.error;

public enum ErrorMessages {
	USER_NOT_FOUND("User not found"),
    COURSE_NOT_FOUND("Course not found"),
    TOPIC_EXISTS("Topic already exists"),
    TOPIC_NOT_FOUND("The topic with the specified id was not found");
    // Add more error messages here as needed

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
