package com.alura.domain.model.service;

import jakarta.validation.Valid;
import java.util.List;

import com.alura.data.remote.dto.TopicDto;
import com.alura.data.remote.dto.UpdateTopicDto;

public interface ITopicService {

    void create(TopicDto data);

    void updateTopic(Long id, @Valid UpdateTopicDto data);

    List<TopicDto> getAll();

    TopicDto getById(Long id);
}
