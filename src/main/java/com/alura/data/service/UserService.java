package com.alura.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.data.remote.dto.UserDto;
import com.alura.data.repository.UserRepository;
import com.alura.domain.model.User;
import com.alura.domain.service.IUserService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

@Service
public class UserService implements IUserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void create(UserDto data) {
		
		if (Boolean.TRUE.equals(userRepository.existsByName(data.name()))) {
			throw new ValidationError(ErrorMessages.USER_EXISTS.getMessage());
		}
		
		if (Boolean.TRUE.equals(userRepository.existsByEmail(data.email()))) {
			throw new ValidationError(ErrorMessages.EMAIL_EXISTS.getMessage());
		}

		User user = data.toUser();
		userRepository.save(user);
	}

	@Override
	public UserDto findById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.USER_NOT_FOUND.getMessage()));
		return new UserDto(user);
	}

	@Override
	public void updateService(Long id, UserDto data) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ValidationError(ErrorMessages.USER_NOT_FOUND.getMessage()));
		
		if (Boolean.TRUE.equals(userRepository.existsByName(data.name()))) {
			throw new ValidationError(ErrorMessages.USER_EXISTS.getMessage());
		}
		
		if (Boolean.TRUE.equals(userRepository.existsByEmail(data.email()))) {
			throw new ValidationError(ErrorMessages.EMAIL_EXISTS.getMessage());
		}
		
		user.updateData(data);
	}
}
