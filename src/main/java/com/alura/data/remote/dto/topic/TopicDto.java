package com.alura.data.remote.dto.topic;

import java.util.List;

import com.alura.domain.model.Response;
import com.alura.domain.model.User;
import com.alura.domain.model.course.Course;
import com.alura.domain.model.topic.Topic;
import com.alura.domain.model.topic.TopicStatus;

import jakarta.validation.constraints.NotNull;

public record TopicDto(Long id, @NotNull String title, @NotNull String message, @NotNull TopicStatus status,
		@NotNull Long userId, @NotNull Long courseId, List<Long> responsesId) {
	
	public TopicDto(Topic topic) {
        this(topic.getId(), topic.getTitle(), 
				topic.getMessage(), 
				topic.getStatus(), 
				topic.getAuthor().getId(), 
				topic.getCourse().getId(), topic.getResponses().stream().map(Response::getId).toList());
    }

	
	public Topic toTopic(User user, Course course) {
		Topic topic = new Topic(this.title, this.message, course);
		topic.setStatus(this.status);
		topic.setAuthor(user);
		topic.setCreationDate();
		return topic;
	}
}
