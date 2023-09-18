package com.alura.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.data.remote.dto.response.ResponseDto;
import com.alura.data.repository.ResponseRepository;
import com.alura.data.repository.TopicRepository;
import com.alura.data.repository.UserRepository;
import com.alura.domain.model.Response;
import com.alura.domain.model.User;
import com.alura.domain.model.topic.Topic;
import com.alura.domain.service.IResponseService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

@Service
public class ResponseService implements IResponseService {

	private final ResponseRepository responseRepository;
	private final TopicRepository topicRepository;
	private final UserRepository userRepository;

	@Autowired
	public ResponseService(ResponseRepository responseRepository, TopicRepository topicRepository,
			UserRepository userRepository) {
		this.responseRepository = responseRepository;
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
	}

	@Override
	public ResponseDto create(ResponseDto data) {
		validateResponseExists(data);
		Topic topic = getTopic(data);
		User user = getUser(data);
		Response response = data.toResponse(topic, user);
		responseRepository.save(response);
		return new ResponseDto(response);
	}

	private void validateResponseExists(ResponseDto data) {
		if (responseRepository.existsByTopicAndMessage(data.topicId(), data.message())) {
			throw new ValidationError(ErrorMessages.USER_EXISTS.getMessage());
		}
	}

	@Override
	public ResponseDto findById(Long id) {
		Response response = responseRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.RESPONSE_NOT_FOUND.getMessage()));
		return new ResponseDto(response);
	}

	@Override
	public ResponseDto updateService(Long id, ResponseDto data) {
		validateResponseExists(data);
		Response response = responseRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.RESPONSE_NOT_FOUND.getMessage()));
		Topic topic = getTopic(data);
		User user = getUser(data);
		response.update(data, topic, user);
		return new ResponseDto(response);
	}
	
	private User getUser(ResponseDto data) {
		return userRepository.findById(data.userId())
				.orElseThrow(() -> new ValidationError(ErrorMessages.USER_NOT_FOUND.getMessage()));
	}

	private Topic getTopic(ResponseDto data) {
		return topicRepository.findById(data.topicId())
				.orElseThrow(() -> new ValidationError(ErrorMessages.TOPIC_NOT_FOUND.getMessage()));
	}
}
