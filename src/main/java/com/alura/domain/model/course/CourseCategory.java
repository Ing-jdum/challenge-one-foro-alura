package com.alura.domain.model.course;

import java.util.List;

import com.alura.data.remote.dto.course.CourseCategoryDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Table(name = "course_category")
@Entity(name = "CourseCategory")
@EqualsAndHashCode(of = "id")
public class CourseCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;
	private String description;
	@OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
	List<Course> courses;

	public CourseCategory() {
	}

	public CourseCategory(CourseCategoryDto data) {
		this.category = data.category();
		this.description = data.description();
	}

	public Long getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void updateData(CourseCategoryDto data) {
		if (data.category() != null) {
			this.category = data.category();
		}
		if (data.description() != null) {
			this.description = data.description();
		}

	}

}
