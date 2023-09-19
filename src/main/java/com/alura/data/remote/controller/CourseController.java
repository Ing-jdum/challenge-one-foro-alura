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

import com.alura.data.remote.dto.course.CourseDto;
import com.alura.data.repository.CourseRepository;
import com.alura.domain.service.ICourseService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/course")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

	private final CourseRepository courseRepository;
	private final ICourseService courseService;

	@Autowired
	public CourseController(CourseRepository courseRepository, ICourseService courseService) {
		this.courseRepository = courseRepository;
		this.courseService = courseService;
	}

	@PostMapping
	@Operation(summary = "Create a new course")
	public ResponseEntity<CourseDto> create(@RequestBody @Valid CourseDto data,
			UriComponentsBuilder uriComponentsBuilder) {
		CourseDto courseDto = courseService.create(data);
		URI url = uriComponentsBuilder.path("/course/{id}").buildAndExpand(data.id()).toUri();
		return ResponseEntity.created(url).body(courseDto);
	}

	@GetMapping("/all")
	@Operation(summary = "Get a paginated list of all courses")
	public ResponseEntity<Page<CourseDto>> getAll(@PageableDefault(size = 2) Pageable pagination) {
		return ResponseEntity.ok(courseRepository.findAll(pagination).map(CourseDto::new));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a course by its ID")
	public ResponseEntity<CourseDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(courseService.findById(id));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a course by its ID")
	@Transactional
	public ResponseEntity<CourseDto> updateById(@PathVariable Long id, @RequestBody CourseDto data) {
		return ResponseEntity.ok(courseService.updateService(id, data));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a course by its ID")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		if (courseService.findById(id) == null) {
			throw new ValidationError(ErrorMessages.COURSE_NOT_FOUND.getMessage());
		}
		courseRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
