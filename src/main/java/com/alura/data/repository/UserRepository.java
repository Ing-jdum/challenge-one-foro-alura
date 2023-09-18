package com.alura.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.alura.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByName(String name);

	Boolean existsByEmail(String email);

	UserDetails findByName(String name);
}
