package com.alura.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.domain.model.course.Course;
import com.alura.domain.model.course.CourseCategory;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long> {

	Object existsByCategory(String category);

}
