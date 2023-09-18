package com.alura.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.data.remote.dto.course.CourseCategoryDto;
import com.alura.data.repository.CourseCategoryRepository;
import com.alura.domain.model.course.CourseCategory;
import com.alura.domain.service.ICourseCategoryService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

import jakarta.transaction.Transactional;

@Service
public class CourseCategoryService implements ICourseCategoryService {

	private final CourseCategoryRepository courseCategoryRepository;

	@Autowired
	public CourseCategoryService(CourseCategoryRepository courseCategoryRepository) {
		this.courseCategoryRepository = courseCategoryRepository;
	}

	@Override
	public CourseCategoryDto create(CourseCategoryDto data) {
		validateCourseExists(data);
		CourseCategory category = new CourseCategory(data);
		courseCategoryRepository.save(category);
		return new CourseCategoryDto(category);
	}

	@Override
	public CourseCategoryDto findById(Long id) {
		CourseCategory category = courseCategoryRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));
		return new CourseCategoryDto(category);
	}

	@Override
	@Transactional
	public CourseCategoryDto updateService(Long id, CourseCategoryDto data) {
		validateCourseExists(data);
		CourseCategory category = courseCategoryRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));
		category.updateData(data);
		return new CourseCategoryDto(category);
	}

	private void validateCourseExists(CourseCategoryDto data) {
		if (Boolean.TRUE.equals(courseCategoryRepository.existsByCategory(data.category()))) {
			throw new ValidationError(ErrorMessages.CATEGORY_ALREADY_EXISTS.getMessage());
		}
	}
}
