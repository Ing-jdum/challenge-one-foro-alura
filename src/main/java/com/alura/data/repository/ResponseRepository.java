package com.alura.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alura.domain.model.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
	
	@Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Response r WHERE r.topic.id = ?1 AND r.message = ?2")
    boolean existsByTopicAndMessage(Long topicId, String message);
}
