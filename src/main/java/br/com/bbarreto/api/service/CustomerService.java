package br.com.bbarreto.api.service;

import java.util.List;

import br.com.bbarreto.api.dto.CustomerRequestDTO;
import br.com.bbarreto.api.dto.CustomerResponseDTO;

public interface CustomerService {

	boolean exists(Long id);

	CustomerResponseDTO findById(Long id);

	List<CustomerResponseDTO> findAll();

	CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO);

	CustomerResponseDTO update(Long id, CustomerRequestDTO customerRequestDTO);

	void deleteById(Long id);

}
