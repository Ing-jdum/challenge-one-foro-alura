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

import com.alura.data.remote.dto.user.UserDto;
import com.alura.data.repository.UserRepository;
import com.alura.domain.service.IUserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserRepository userRepository;
	private IUserService userService;

	@Autowired
	public UserController(UserRepository userRepository, IUserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto data, UriComponentsBuilder uriComponentsBuilder) {
		UserDto user = userService.create(data);
		URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(data.id()).toUri();
		return ResponseEntity.created(url).body(user);
	}

	@GetMapping("/all")
	public ResponseEntity<Page<UserDto>> getAll(@PageableDefault(size = 2) Pageable pagination) {
		return ResponseEntity.ok(userRepository.findAll(pagination).map(UserDto::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UserDto> updateById(@PathVariable Long id, @RequestBody UserDto data) {
		return ResponseEntity.ok(userService.updateService(id, data));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
