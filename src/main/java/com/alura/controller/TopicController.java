package com.alura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.domain.topic.CreateTopicDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/topics")
public class TopicController {
	
	@PostMapping
	public ResponseEntity create(@RequestBody @Valid CreateTopicDto data) {
		return null;
	}
}
