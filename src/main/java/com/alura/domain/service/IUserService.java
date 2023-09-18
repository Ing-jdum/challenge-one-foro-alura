package com.alura.domain.service;

import com.alura.data.remote.dto.user.UserDto;

public interface IUserService {

	void create(UserDto data);

	UserDto findById(Long id);

	void updateService(Long id, UserDto data);
}
