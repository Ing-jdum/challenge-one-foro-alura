package com.alura.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alura.domain.model.topic.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

	@Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Topic t WHERE t.title = ?1 AND t.message = ?2")
    boolean existsByTitleAndMessage(String title, String message);
}
