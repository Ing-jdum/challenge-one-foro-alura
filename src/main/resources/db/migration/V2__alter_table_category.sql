ALTER TABLE courses
ADD CONSTRAINT fk_course_category
FOREIGN KEY (course_category_id) REFERENCES course_category(id);