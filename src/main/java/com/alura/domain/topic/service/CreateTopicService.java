package com.alura.domain.topic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.domain.course.Course;
import com.alura.domain.course.CourseRepository;
import com.alura.domain.topic.Topic;
import com.alura.domain.topic.TopicDto;
import com.alura.domain.topic.TopicRepository;
import com.alura.domain.user.User;
import com.alura.domain.user.UserRepository;
import com.alura.infra.error.CourseNotFoundException;
import com.alura.infra.error.TopicExistsException;
import com.alura.infra.error.UserNotFoundException;

@Service
public class CreateTopicService {

	private TopicRepository topicRepository;
	private UserRepository userRepository;
	private CourseRepository courseRepository;

	@Autowired
	public CreateTopicService(TopicRepository topicRepository, UserRepository userRepository,
			CourseRepository courseRepository) {
		this.topicRepository = topicRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}

	public void create(TopicDto data) {
		User user = userRepository.findById(data.userId())
				.orElseThrow(() -> new UserNotFoundException("The user does not exists on the db"));

		Course course = courseRepository.findById(data.courseId())
				.orElseThrow(() -> new CourseNotFoundException("The course already exists on the db"));

		Boolean topicExists = topicRepository.existsByTitleAndMessage(data.title(), data.message());

		if (Boolean.TRUE.equals(topicExists)) {
			throw new TopicExistsException("The topic is already created in the db");
		}
		
		Topic topic = data.toTopic(user, course);
		topicRepository.save(topic);
	}
}
