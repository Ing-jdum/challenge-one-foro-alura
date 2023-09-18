package com.alura.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.data.remote.dto.topic.TopicDto;
import com.alura.data.repository.CourseRepository;
import com.alura.data.repository.TopicRepository;
import com.alura.data.repository.UserRepository;
import com.alura.domain.model.User;
import com.alura.domain.model.course.Course;
import com.alura.domain.model.topic.Topic;
import com.alura.domain.service.ITopicService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

@Service
public class TopicService implements ITopicService {

	private final TopicRepository topicRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;

	@Autowired
	public TopicService(TopicRepository topicRepository, UserRepository userRepository,
			CourseRepository courseRepository) {
		this.topicRepository = topicRepository;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public TopicDto create(TopicDto data) {
		validateTopicExists(data);
		User user = getUser(data);
		Course course = getCourse(data);

		Topic topic = data.toTopic(user, course);
		topicRepository.save(topic);
		return (new TopicDto(topic));
	}

	private void validateTopicExists(TopicDto data) {
		if (topicRepository.existsByTitleAndMessage(data.title(), data.message())) {
			throw new ValidationError(ErrorMessages.TOPIC_EXISTS.getMessage());
		}
	}

	@Override
	public TopicDto updateTopic(Long id, TopicDto data) {
		validateTopicExists(data);
		Topic topic = topicRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.TOPIC_NOT_FOUND.getMessage()));
		
		TopicDto existingTopic = new TopicDto(topic);
		User user = getUser(data);
		Course course = getCourse(data);
		topic.updateData(data, course, user);
		return new TopicDto(topic);
	}

	private Course getCourse(TopicDto data) {
		Course course = null;
		if (data.courseId() != null) {
			course = courseRepository.findById(data.courseId())
					.orElseThrow(() -> new ValidationError(ErrorMessages.COURSE_NOT_FOUND.getMessage()));
		}
		return course;
	}

	private User getUser(TopicDto data) {
		User user = null;
		if (data.userId() != null) {
			user = userRepository.findById(data.userId())
					.orElseThrow(() -> new ValidationError(ErrorMessages.USER_NOT_FOUND.getMessage()));
		}
		return user;
	}

	@Override
	public TopicDto findById(Long id) {
		Topic topic = topicRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.TOPIC_NOT_FOUND.getMessage()));
		return new TopicDto(topic);
	}
}
