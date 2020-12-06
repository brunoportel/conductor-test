package br.com.bbarreto.api.service;

import java.util.List;

import br.com.bbarreto.api.dto.CompanyRequestDTO;
import br.com.bbarreto.api.dto.CompanyResponseDTO;

public interface CompanyService {
	boolean exists(Long id);

	CompanyResponseDTO findById(Long id);

	List<CompanyResponseDTO> findAll();

	CompanyResponseDTO create(CompanyRequestDTO companyRequestDTO);

	CompanyResponseDTO update(Long id, CompanyRequestDTO companyRequestDTO);

	void deleteById(Long id);
}
