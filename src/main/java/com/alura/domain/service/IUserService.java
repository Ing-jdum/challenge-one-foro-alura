package com.alura.domain.service;

import com.alura.data.remote.dto.user.UserDto;

public interface IUserService {

	UserDto create(UserDto data);

	UserDto findById(Long id);

	UserDto updateService(Long id, UserDto data);
}
