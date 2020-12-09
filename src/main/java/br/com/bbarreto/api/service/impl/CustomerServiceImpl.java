package br.com.bbarreto.api.service.impl;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bbarreto.api.dto.CustomerRequestDTO;
import br.com.bbarreto.api.dto.CustomerResponseDTO;
import br.com.bbarreto.api.repository.CustomerRepository;
import br.com.bbarreto.api.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return this.customerRepository.existsById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CustomerResponseDTO findById(Long id) {
		CustomerResponseDTO customerResponseDTO = null;

		var customerModel = this.customerRepository.findById(id).orElse(null);
		if (Objects.nonNull(customerModel)) {
			customerResponseDTO = new CustomerResponseDTO(customerModel);
		}

		return customerResponseDTO;
	}

	@Override
	public List<CustomerResponseDTO> findAll() {
		var customerResponseDTOList = new ArrayList<CustomerResponseDTO>();

		this.customerRepository.findAll()
		.forEach(customerModel -> customerResponseDTOList.add(new CustomerResponseDTO(customerModel)));

		return customerResponseDTOList;
	}

	@Transactional
	@Override
	public CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO) {
		var customerModel = customerRequestDTO.toModel();
		customerModel.setActive(true);
		customerModel.setCreateDate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		customerModel.setLastUpdate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		customerModel = this.customerRepository.save(customerModel);

		return new CustomerResponseDTO(customerModel);
	}

	@Transactional
	@Override
	public CustomerResponseDTO update(Long id, CustomerRequestDTO customerRequestDTO) {
		var customerModel = this.customerRepository.findById(id)
				.orElse(null);

		if (Objects.nonNull(customerModel)) {
			customerModel.setFirstName(customerRequestDTO.getFirstName());
			customerModel.setLastName(customerRequestDTO.getLastName());
			customerModel.setEmail(customerRequestDTO.getEmail());
			customerModel.setLastUpdate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			customerModel = this.customerRepository.save(customerModel);
			return new CustomerResponseDTO(customerModel);
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		this.customerRepository.deleteById(id);
	}
}
