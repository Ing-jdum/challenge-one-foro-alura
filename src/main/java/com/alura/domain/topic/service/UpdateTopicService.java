package com.alura.domain.topic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.domain.course.Course;
import com.alura.domain.course.CourseRepository;
import com.alura.domain.topic.Topic;
import com.alura.domain.topic.TopicRepository;
import com.alura.domain.topic.UpdateTopicDto;
import com.alura.domain.user.User;
import com.alura.domain.user.UserRepository;
import com.alura.infra.error.CourseNotFoundException;
import com.alura.infra.error.TopicNotFoundException;
import com.alura.infra.error.UserNotFoundException;

import jakarta.validation.Valid;

@Service
public class UpdateTopicService {

	private TopicRepository topicRepository;
	private UserRepository userRepository;
	private CourseRepository courseRepository;

	@Autowired
	public UpdateTopicService(TopicRepository topicRepository, UserRepository userRepository,
			CourseRepository courseRepository) {
		this.topicRepository = topicRepository;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
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

}
