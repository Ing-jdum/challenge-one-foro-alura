package com.alura;

import com.alura.domain.topic.TopicDto;
import com.alura.domain.topic.UpdateTopicDto;
import jakarta.validation.Valid;
import java.util.List;

public interface ITopicService {

    void create(TopicDto data);

    void updateTopic(Long id, @Valid UpdateTopicDto data);

    List<TopicDto> getAll();

    TopicDto getById(Long id);
}
