package com.alura.domain.topic;

import com.alura.domain.course.Course;
import com.alura.domain.user.User;

import jakarta.validation.constraints.NotNull;

public record TopicDto(@NotNull String title, @NotNull String message, @NotNull TopicStatus status,
		@NotNull Long userId, @NotNull Long courseId) {

	public Topic toTopic(User user, Course course) {
		Topic topic = new Topic(this.title, this.message, course);
		topic.setStatus(this.status);
		topic.setAuthor(user);
		topic.setCreationDate();
		return topic;
	}

	public static TopicDto fromTopic(Topic topic) {
		return new TopicDto(topic.getTitle(), 
				topic.getMessage(), 
				topic.getStatus(), 
				topic.getAuthor().getId(), 
				topic.getCourse().getId());
	}
}
