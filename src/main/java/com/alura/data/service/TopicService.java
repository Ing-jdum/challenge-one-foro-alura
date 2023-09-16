package com.alura.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.data.remote.dto.TopicDto;
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
	public void create(TopicDto data) {
		User user = getUser(data);
		Course course = getCourse(data);
		Boolean topicExists = topicRepository.existsByTitleAndMessage(data.title(), data.message());

		if (Boolean.TRUE.equals(topicExists)) {
			throw new ValidationError(ErrorMessages.TOPIC_EXISTS.getMessage());
		}

		Topic topic = data.toTopic(user, course);
		topicRepository.save(topic);
	}

	@Override
	public void updateTopic(Long id, TopicDto data) {
		User user = getUser(data);
		Course course = getCourse(data);

		Topic topic = topicRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.TOPIC_NOT_FOUND.getMessage()));

		topic.updateData(data, course, user);
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
