package com.alura.domain.service;

import com.alura.data.remote.dto.ResponseDto;

public interface IResponseService {

	void create(ResponseDto data);

	ResponseDto findById(Long id);

	void updateService(Long id, ResponseDto data);
}
