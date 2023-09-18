package com.alura.data.remote.dto;

import java.time.LocalDateTime;

import com.alura.domain.model.Response;
import com.alura.domain.model.User;
import com.alura.domain.model.topic.Topic;

import jakarta.validation.constraints.NotNull;

public record ResponseDto(Long id, @NotNull String message, @NotNull Long topicId, @NotNull LocalDateTime creationDate,
		@NotNull Long userId, @NotNull Boolean solution) {

	public ResponseDto(Response response) {
		this(response.getId(), response.getMessage(), response.getTopic().getId(), response.getCreationDate(),
				response.getAuthor().getId(), response.getSolution());
	}
	
	public Response toResponse(Topic topic, User user) {
		return new Response(this, topic, user);
	}
}
