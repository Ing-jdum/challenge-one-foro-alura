package com.alura.data.remote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.data.remote.dto.user.UserAuthDto;
import com.alura.domain.model.User;
import com.alura.infra.security.remote.TokenService;
import com.alura.infra.security.remote.dto.JWTTokenDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	private TokenService tokenService;

	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
		super();
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	@PostMapping
	public ResponseEntity<JWTTokenDto> autenticarUsuario(@RequestBody @Valid UserAuthDto userAuthDto) {
		Authentication token = new UsernamePasswordAuthenticationToken(userAuthDto.name(), userAuthDto.password());
		var authenticatedUser = authenticationManager.authenticate(token);
		var JWTtoken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
		return ResponseEntity.ok(new JWTTokenDto(JWTtoken));
	}

}
