package com.alura.data.remote.dto;

import com.alura.domain.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserDto(@NotNull Long id, @NotNull String name, @NotNull @Email String email, @NotNull String password) {

	public UserDto(User user) {
		this(user.getId(), user.getName(), user.getEmail(), user.getPassword());
	}

	public User toUser() {
		return new User(this);
	}
}
