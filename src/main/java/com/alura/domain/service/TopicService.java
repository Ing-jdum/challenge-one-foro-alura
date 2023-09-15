package com.alura.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.data.remote.dto.TopicDto;
import com.alura.data.remote.dto.UpdateTopicDto;
import com.alura.data.repository.CourseRepository;
import com.alura.data.repository.TopicRepository;
import com.alura.data.repository.UserRepository;
import com.alura.domain.model.User;
import com.alura.domain.model.course.Course;
import com.alura.domain.model.service.ITopicService;
import com.alura.domain.model.topic.Topic;
import com.alura.infra.error.validations.ValidationError;

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
				.orElseThrow(() -> new ValidationError("The user does not exist in the database"));

		Course course = courseRepository.findById(data.courseId())
				.orElseThrow(() -> new ValidationError("The course does not exist in the database"));

		Boolean topicExists = topicRepository.existsByTitleAndMessage(data.title(), data.message());

		if (Boolean.TRUE.equals(topicExists)) {
			throw new ValidationError("The topic already exists in the database");
		}

		Topic topic = data.toTopic(user, course);
		topicRepository.save(topic);
	}

	public void updateTopic(Long id, @Valid UpdateTopicDto data) {
		User user = null;
		Course course = null;

		if (data.userId() != null) {
			user = userRepository.findById(data.userId())
					.orElseThrow(() -> new ValidationError("User was not found"));
		}
		if (data.courseId() != null) {
			course = courseRepository.findById(data.courseId())
					.orElseThrow(() -> new ValidationError("User was not found"));
		}

		Topic topic = topicRepository.findById(id)
				.orElseThrow(() -> new ValidationError("The topic was not found"));

		topic.updateData(data, course, user);
	}

	public List<TopicDto> getAll() {
		return topicRepository.findAll().stream().map(TopicDto::fromTopic).toList();
	}

	public TopicDto getById(Long id) {
		Topic topic = topicRepository.findById(id)
				.orElseThrow(() -> new ValidationError("The topic with the specified id was not found"));
		return TopicDto.fromTopic(topic);
	}
}
