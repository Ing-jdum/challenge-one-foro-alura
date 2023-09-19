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

import com.alura.data.remote.dto.course.CourseCategoryDto;
import com.alura.data.repository.CourseCategoryRepository;
import com.alura.domain.service.ICourseCategoryService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/course/category")
@SecurityRequirement(name = "bearer-key")
public class CourseCategoryController {

	private final CourseCategoryRepository courseCategoryRepository;
	private final ICourseCategoryService courseCategoryService;

	@Autowired
	public CourseCategoryController(CourseCategoryRepository courseCategoryRepository,
			ICourseCategoryService courseCategoryService) {
		this.courseCategoryRepository = courseCategoryRepository;
		this.courseCategoryService = courseCategoryService;
	}

	@PostMapping
	@Operation(summary = "Create a new course category")
	public ResponseEntity<CourseCategoryDto> create(@RequestBody @Valid CourseCategoryDto data,
			UriComponentsBuilder uriComponentsBuilder) {
		CourseCategoryDto courseDto = courseCategoryService.create(data);
		URI url = uriComponentsBuilder.path("/course/{id}").buildAndExpand(data.id()).toUri();
		return ResponseEntity.created(url).body(courseDto);
	}

	@GetMapping("/all")
	@Operation(summary = "Get a paginated list of all course categories")
	public ResponseEntity<Page<CourseCategoryDto>> getAll(@PageableDefault(size = 2) Pageable pagination) {
		return ResponseEntity.ok(courseCategoryRepository.findAll(pagination).map(CourseCategoryDto::new));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a course category by its ID")
	public ResponseEntity<CourseCategoryDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(courseCategoryService.findById(id));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a course category by its ID")
	@Transactional
	public ResponseEntity<CourseCategoryDto> updateById(@PathVariable Long id, @RequestBody CourseCategoryDto data) {
		return ResponseEntity.ok(courseCategoryService.updateService(id, data));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a course category by its ID")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		if (courseCategoryService.findById(id) == null) {
			throw new ValidationError(ErrorMessages.CATEGORY_NOT_FOUND.getMessage());
		}
		courseCategoryRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
