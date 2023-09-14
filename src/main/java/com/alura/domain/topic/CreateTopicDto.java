package com.alura.domain.topic;

import java.time.LocalDateTime;
import java.util.List;

import com.alura.domain.Course;
import com.alura.domain.Response;
import com.alura.domain.User;

import jakarta.validation.constraints.NotNull;

public record CreateTopicDto(@NotNull Long id, @NotNull String title, @NotNull String message,
		@NotNull LocalDateTime creationDate, @NotNull TopicStatus status, @NotNull User author, @NotNull Course course,
		@NotNull List<Response> responses) {
}
