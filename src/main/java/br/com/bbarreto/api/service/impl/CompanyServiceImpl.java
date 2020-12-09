package br.com.bbarreto.api.service.impl;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bbarreto.api.dto.CompanyRequestDTO;
import br.com.bbarreto.api.dto.CompanyResponseDTO;
import br.com.bbarreto.api.dto.IdDTO;
import br.com.bbarreto.api.model.CustomerModel;
import br.com.bbarreto.api.repository.CompanyCustomerRepository;
import br.com.bbarreto.api.repository.CompanyRepository;
import br.com.bbarreto.api.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CompanyCustomerRepository companyCustomerRepository;

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return this.companyRepository.existsById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CompanyResponseDTO findById(Long id) {
		CompanyResponseDTO companyResponseDTO = null;

		var companyModel = this.companyRepository.findById(id).orElse(null);
		if (Objects.nonNull(companyModel)) {
			companyResponseDTO = new CompanyResponseDTO(companyModel, companyModel.getCustomers());
		}

		return companyResponseDTO;
	}

	@Transactional(readOnly = true)
	@Override
	public List<CompanyResponseDTO> findAll() {
		var companyResponseDTOList = new ArrayList<CompanyResponseDTO>();

		this.companyRepository.findAll()
		.forEach(companyModel -> companyResponseDTOList.add(new CompanyResponseDTO(companyModel, null)));

		return companyResponseDTOList;
	}

	@Transactional
	@Override
	public Long create(CompanyRequestDTO companyRequestDTO) {
		var companyModel = companyRequestDTO.toModel();
		companyModel.setActive(true);
		companyModel.setCreateDate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		companyModel.setLastUpdate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		companyModel.setCustomers(this.createNewCustomers(companyRequestDTO.getCustomers()));
		companyModel = this.companyRepository.save(companyModel);
		return companyModel.getId();
	}

	@Transactional
	@Override
	public void update(Long id, CompanyRequestDTO companyRequestDTO) {
		var companyModel = this.companyRepository.findById(id)
				.orElse(null);

		if (Objects.nonNull(companyModel)) {
			// Delete all company customers
			this.companyCustomerRepository.deleteByCompanyId(id);

			companyModel.setName(companyRequestDTO.getName());
			companyModel.setEmail(companyRequestDTO.getEmail());
			companyModel.setLastUpdate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			companyModel.setCustomers(this.createNewCustomers(companyRequestDTO.getCustomers()));
			this.companyRepository.save(companyModel);
		}
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		this.companyRepository.deleteById(id);
	}

	private Set<CustomerModel> createNewCustomers(Set<IdDTO> customersDTO) {
		var customersModel = new HashSet<CustomerModel>();

		if (Objects.nonNull(customersDTO)) {
			customersDTO.removeIf(customerDTO -> Objects.isNull(customerDTO.getId()));

			if (!customersDTO.isEmpty()) {
				customersDTO.stream().mapToLong(IdDTO::getId).forEach(customerId -> customersModel.add(new CustomerModel(customerId)));
			}
		}

		return customersModel;
	}
}
