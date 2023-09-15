package com.alura.domain.service;

import com.alura.data.remote.dto.TopicDto;

public interface ITopicService {

	void create(TopicDto data);

	void updateTopic(Long id, TopicDto data);

	TopicDto findById(Long id);
}
