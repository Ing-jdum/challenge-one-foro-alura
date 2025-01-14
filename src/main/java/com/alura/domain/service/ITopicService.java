package com.alura.domain.service;

import com.alura.data.remote.dto.topic.TopicDto;

public interface ITopicService {

	TopicDto create(TopicDto data);

	TopicDto updateTopic(Long id, TopicDto data);

	TopicDto findById(Long id);
}
