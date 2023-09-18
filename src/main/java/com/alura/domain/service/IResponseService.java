package com.alura.domain.service;

import com.alura.data.remote.dto.ResponseDto;

public interface IResponseService {

	ResponseDto create(ResponseDto data);

	ResponseDto findById(Long id);

	ResponseDto updateService(Long id, ResponseDto data);
}
