package com.alura.data.remote.dto.course;

import com.alura.domain.model.course.Course;

import jakarta.validation.constraints.NotNull;

public record CourseDto(Long id, @NotNull String name, @NotNull Long courseCategoryId) {

	public CourseDto(Course course) {
		this(course.getId(), course.getName(), course.getCategory().getId());
	}

}
