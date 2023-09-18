package com.alura.data.remote.dto.course;

import com.alura.domain.model.course.CourseCategory;

import jakarta.validation.constraints.NotNull;

public record CourseCategoryDto(Long id, @NotNull String category, @NotNull String description) {

	public CourseCategoryDto(CourseCategory courseCategory) {
		this(courseCategory.getId(), courseCategory.getCategory(), courseCategory.getDescription());
	}
}
