package com.alura.data.remote.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.data.remote.dto.TopicDto;
import com.alura.data.repository.TopicRepository;
import com.alura.domain.service.ITopicService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topics")
public class TopicController {

	ITopicService topicService;
	TopicRepository topicRepository;

	@Autowired
	public TopicController(ITopicService topicService, TopicRepository topicRepository) {
		this.topicService = topicService;
		this.topicRepository = topicRepository;
	}

	@PostMapping
	public ResponseEntity<TopicDto> create(@RequestBody @Valid TopicDto data,
			UriComponentsBuilder uriComponentsBuilder) {
		TopicDto topic = topicService.create(data);
		URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(data.id()).toUri();
		return ResponseEntity.created(url).body(topic);
	}

	@GetMapping("/all")
	public ResponseEntity<Page<TopicDto>> getAll(@PageableDefault(size = 2) Pageable pagination) {
		return ResponseEntity.ok(topicRepository.findAll(pagination).map(TopicDto::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(topicService.findById(id));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicDto> updateById(@PathVariable Long id, @RequestBody TopicDto data) {
		return ResponseEntity.ok(topicService.updateTopic(id, data));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		topicRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
