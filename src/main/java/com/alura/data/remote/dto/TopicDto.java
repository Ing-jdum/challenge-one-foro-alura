package com.alura.data.remote.dto;

import com.alura.domain.model.User;
import com.alura.domain.model.course.Course;
import com.alura.domain.model.topic.Topic;
import com.alura.domain.model.topic.TopicStatus;

import jakarta.validation.constraints.NotNull;

public record TopicDto(@NotNull String title, @NotNull String message, @NotNull TopicStatus status,
		@NotNull Long userId, @NotNull Long courseId) {
	
	public TopicDto(Topic topic) {
        this(topic.getTitle(), 
				topic.getMessage(), 
				topic.getStatus(), 
				topic.getAuthor().getId(), 
				topic.getCourse().getId());
    }

	
	public Topic toTopic(User user, Course course) {
		Topic topic = new Topic(this.title, this.message, course);
		topic.setStatus(this.status);
		topic.setAuthor(user);
		topic.setCreationDate();
		return topic;
	}
}
