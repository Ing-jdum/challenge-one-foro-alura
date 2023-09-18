package com.alura.domain.service;

import com.alura.data.remote.dto.course.CourseCategoryDto;

public interface ICourseCategoryService {
	CourseCategoryDto create(CourseCategoryDto data);

	CourseCategoryDto findById(Long id);

	CourseCategoryDto updateService(Long id, CourseCategoryDto data);
}
