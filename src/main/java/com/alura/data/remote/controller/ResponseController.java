package com.alura.data.remote.controller;

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

import com.alura.data.remote.dto.ResponseDto;
import com.alura.data.repository.ResponseRepository;
import com.alura.domain.service.IResponseService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/responses")
public class ResponseController {
	
	IResponseService responseService;
	ResponseRepository responseRepository;
	
	@Autowired
	public ResponseController(IResponseService responseService, ResponseRepository responseRepository) {
		this.responseRepository = responseRepository;
		this.responseService = responseService;
	}
	
	@PostMapping
	public ResponseEntity<ResponseDto> create(@RequestBody @Valid ResponseDto data) {
		responseService.create(data);
		return ResponseEntity.ok(data);
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
	public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody ResponseDto data) {
		responseService.updateService(id, data);
		return ResponseEntity.ok("Item updated");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		responseRepository.deleteById(id);
		return ResponseEntity.ok("Item deleted");
	}

}
