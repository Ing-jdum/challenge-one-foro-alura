package com.alura.data.remote.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.data.remote.dto.response.ResponseDto;
import com.alura.data.repository.ResponseRepository;
import com.alura.domain.service.IResponseService;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.validations.ValidationError;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/responses")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {

	IResponseService responseService;
	ResponseRepository responseRepository;

	@Autowired
	public ResponseController(IResponseService responseService, ResponseRepository responseRepository) {
		this.responseRepository = responseRepository;
		this.responseService = responseService;
	}

	@PostMapping
	public ResponseEntity<ResponseDto> create(@RequestBody @Valid ResponseDto data,
			UriComponentsBuilder uriComponentsBuilder) {
		ResponseDto responseDto = responseService.create(data);
		URI url = uriComponentsBuilder.path("/response/{id}").buildAndExpand(data.id()).toUri();
		return ResponseEntity.created(url).body(responseDto);
	}

	@GetMapping("/all")
	public ResponseEntity<Page<ResponseDto>> getAll(@PageableDefault(size = 2) Pageable pagination) {
		return ResponseEntity.ok(responseRepository.findAll(pagination).map(ResponseDto::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(responseService.findById(id));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ResponseDto> updateById(@PathVariable Long id, @RequestBody ResponseDto data) {
		return ResponseEntity.ok(responseService.updateService(id, data));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		if (responseService.findById(id) == null) {
			throw new ValidationError(ErrorMessages.RESPONSE_NOT_FOUND.getMessage());
		}
		responseRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
