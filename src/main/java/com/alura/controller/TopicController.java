package com.alura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.ITopicService;
import com.alura.domain.topic.TopicDto;
import com.alura.domain.topic.TopicRepository;
import com.alura.domain.topic.UpdateTopicDto;
import com.alura.domain.topic.service.TopicService;

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
	public ResponseEntity<TopicDto> create(@RequestBody @Valid TopicDto data) {
		topicService.create(data);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/all")
	public ResponseEntity<List<TopicDto>> getAll() {
		return ResponseEntity.ok(topicService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(topicService.getById(id));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody @Valid UpdateTopicDto data) {
		topicService.updateTopic(id, data);
		return ResponseEntity.ok("Item updated");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		topicRepository.deleteById(id);
		return ResponseEntity.ok("Item deleted");
	}
}
