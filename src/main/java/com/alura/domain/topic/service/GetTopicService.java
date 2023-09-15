package com.alura.domain.topic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.domain.topic.Topic;
import com.alura.domain.topic.TopicDto;
import com.alura.domain.topic.TopicRepository;
import com.alura.infra.error.TopicNotFoundException;

@Service
public class GetTopicService {

	private TopicRepository topicRepository;

	@Autowired
	public GetTopicService(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
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
