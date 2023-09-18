package com.alura.data.remote.controller;

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
	public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto data) {
		userService.create(data);
		return ResponseEntity.ok(data);
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
	public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody UserDto data) {
		userService.updateService(id, data);
		return ResponseEntity.ok("Item updated");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		userRepository.deleteById(id);
		return ResponseEntity.ok("Item deleted");
	}

}
