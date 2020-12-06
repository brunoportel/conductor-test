package br.com.bbarreto.api.service.impl;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bbarreto.api.dto.CompanyRequestDTO;
import br.com.bbarreto.api.dto.CompanyResponseDTO;
import br.com.bbarreto.api.exception.ObjectNotFoundException;
import br.com.bbarreto.api.repository.CompanyRepository;
import br.com.bbarreto.api.service.CompanyService;
import br.com.bbarreto.api.util.ConstantsUtils;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return this.companyRepository.existsById(id);
	}

	@Override
	public CompanyResponseDTO findById(Long id) {
		CompanyResponseDTO companyResponseDTO = null;

		var companyModel = this.companyRepository.findById(id).orElse(null);
		if (Objects.nonNull(companyModel)) {
			companyResponseDTO = new CompanyResponseDTO(companyModel);
		}

		return companyResponseDTO;
	}

	@Override
	public List<CompanyResponseDTO> findAll() {
		var companyResponseDTOList = new ArrayList<CompanyResponseDTO>();

		this.companyRepository.findAll()
		.forEach(companyModel -> companyResponseDTOList.add(new CompanyResponseDTO(companyModel)));

		return companyResponseDTOList;
	}

	@Override
	public CompanyResponseDTO create(CompanyRequestDTO companyRequestDTO) {
		var companyModel = companyRequestDTO.toModel();
		companyModel.setActive(true);
		companyModel.setCreateDate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		companyModel.setLastUpdate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));

		this.companyRepository.save(companyModel);

		return new CompanyResponseDTO(companyModel);
	}

	@Override
	public CompanyResponseDTO update(Long id, CompanyRequestDTO companyRequestDTO) {
		var companyModel = this.companyRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(ConstantsUtils.COMPANY_ENTITY, id));

		companyModel.setName(companyRequestDTO.getName());
		companyModel.setEmail(companyRequestDTO.getEmail());
		companyModel.setLastUpdate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));

		this.companyRepository.save(companyModel);
		return new CompanyResponseDTO(companyModel);
	}

	@Override
	public void deleteById(Long id) {
		this.companyRepository.deleteById(id);
	}

}
