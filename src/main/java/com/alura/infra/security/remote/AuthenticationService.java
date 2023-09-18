package com.alura.infra.security.remote;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alura.data.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{

	private UserRepository userRepository;
	
	public AuthenticationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByName(username);
	}
	
}