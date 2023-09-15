package com.alura.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.domain.model.course.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
