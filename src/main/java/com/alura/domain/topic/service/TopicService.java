package com.alura.domain.topic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.ITopicService;
import com.alura.domain.course.Course;
import com.alura.domain.course.CourseRepository;
import com.alura.domain.topic.Topic;
import com.alura.domain.topic.TopicDto;
import com.alura.domain.topic.TopicRepository;
import com.alura.domain.topic.UpdateTopicDto;
import com.alura.domain.user.User;
import com.alura.domain.user.UserRepository;
import com.alura.infra.error.CourseNotFoundException;
import com.alura.infra.error.TopicExistsException;
import com.alura.infra.error.TopicNotFoundException;
import com.alura.infra.error.UserNotFoundException;

import jakarta.validation.Valid;

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

	public void create(TopicDto data) {
		User user = userRepository.findById(data.userId())
				.orElseThrow(() -> new UserNotFoundException("The user does not exist in the database"));

		Course course = courseRepository.findById(data.courseId())
				.orElseThrow(() -> new CourseNotFoundException("The course does not exist in the database"));

		Boolean topicExists = topicRepository.existsByTitleAndMessage(data.title(), data.message());

		if (Boolean.TRUE.equals(topicExists)) {
			throw new TopicExistsException("The topic already exists in the database");
		}

		Topic topic = data.toTopic(user, course);
		topicRepository.save(topic);
	}

	public void updateTopic(Long id, @Valid UpdateTopicDto data) {
		User user = null;
		Course course = null;

		if (data.userId() != null) {
			user = userRepository.findById(data.userId())
					.orElseThrow(() -> new UserNotFoundException("User was not found"));
		}
		if (data.courseId() != null) {
			course = courseRepository.findById(data.courseId())
					.orElseThrow(() -> new CourseNotFoundException("User was not found"));
		}

		Topic topic = topicRepository.findById(id)
				.orElseThrow(() -> new TopicNotFoundException("The topic was not found"));

		topic.updateData(data, course, user);
	}

	public List<TopicDto> getAll() {
		return topicRepository.findAll().stream().map(TopicDto::fromTopic).toList();
	}

	public TopicDto getById(Long id) {
		Topic topic = topicRepository.findById(id)
				.orElseThrow(() -> new TopicNotFoundException("The topic with the specified id was not found"));
		return TopicDto.fromTopic(topic);
	}
}
