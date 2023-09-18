package com.alura.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.data.remote.dto.course.CourseDto;
import com.alura.data.repository.CourseCategoryRepository;
import com.alura.data.repository.CourseRepository;
import com.alura.domain.model.course.Course;
import com.alura.domain.model.course.CourseCategory;
import com.alura.domain.service.ICourseService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

import jakarta.transaction.Transactional;

@Service
public class CourseService implements ICourseService {

	private final CourseRepository courseRepository;
	private final CourseCategoryRepository courseCategoryRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository, CourseCategoryRepository courseCategoryRepository) {
		this.courseRepository = courseRepository;
		this.courseCategoryRepository = courseCategoryRepository;
	}

	@Override
	public CourseDto create(CourseDto data) {
		validateCourseExists(data);
		CourseCategory category = courseCategoryRepository.findById(data.courseCategoryId())
				.orElseThrow(() -> new ValidationError(ErrorMessages.CATEGORY_NOT_EXIST.getMessage()));
		Course course = new Course(data, category);
		courseRepository.save(course);
		return new CourseDto(course);
	}

	@Override
	public CourseDto findById(Long id) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.COURSE_NOT_FOUND.getMessage()));
		return new CourseDto(course);
	}

	@Override
	@Transactional
	public CourseDto updateService(Long id, CourseDto data) {
		validateCourseExists(data);
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.COURSE_NOT_FOUND.getMessage()));
		CourseCategory category = null;
		
		if(data.courseCategoryId() != null) {
			category = courseCategoryRepository.findById(data.courseCategoryId())
					.orElseThrow(() -> new ValidationError(ErrorMessages.CATEGORY_NOT_EXIST.getMessage()));
		}
		 

		course.updateData(data, category);
		return new CourseDto(course);
	}

	private void validateCourseExists(CourseDto data) {
		if (Boolean.TRUE.equals(courseRepository.existsByName(data.name()))) {
			throw new ValidationError(ErrorMessages.COURSE_ALREADY_EXISTS.getMessage());
		}
	}
}
