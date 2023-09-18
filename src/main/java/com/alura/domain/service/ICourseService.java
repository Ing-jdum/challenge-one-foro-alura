package com.alura.domain.service;

import com.alura.data.remote.dto.course.CourseDto;

public interface ICourseService {
	CourseDto create(CourseDto data);

	CourseDto findById(Long id);

	CourseDto updateService(Long id, CourseDto data);
}
