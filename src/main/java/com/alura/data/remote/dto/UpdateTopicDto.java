package com.alura.data.remote.dto;

import com.alura.domain.model.User;
import com.alura.domain.model.course.Course;
import com.alura.domain.model.topic.Topic;
import com.alura.domain.model.topic.TopicStatus;

public record UpdateTopicDto(String title, String message, TopicStatus status, Long userId, Long courseId) {

	public Topic toTopic(User user, Course course) {
		Topic topic = new Topic(this.title, this.message, course);
		topic.setStatus(this.status);
		topic.setAuthor(user);
		topic.setCreationDate();
		return topic;
	}

	public static UpdateTopicDto fromTopic(Topic topic) {
		return new UpdateTopicDto(topic.getTitle(), topic.getMessage(), topic.getStatus(), topic.getAuthor().getId(),
				topic.getCourse().getId());
	}
}
