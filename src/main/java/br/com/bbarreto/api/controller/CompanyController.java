package br.com.bbarreto.api.controller;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.bbarreto.api.dto.CompanyRequestDTO;
import br.com.bbarreto.api.dto.CompanyResponseDTO;
import br.com.bbarreto.api.dto.ListResponse;
import br.com.bbarreto.api.exception.ObjectNotFoundException;
import br.com.bbarreto.api.service.CompanyService;
import br.com.bbarreto.api.util.ConstantsUtils;
import br.com.bbarreto.api.util.EndpointUtils;

@RestController
@RequestMapping(EndpointUtils.COMPANY_V1)
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@GetMapping
	public ResponseEntity<ListResponse<CompanyResponseDTO>> findAll() {
		return ResponseEntity.ok(new ListResponse<>(this.companyService.findAll()));
	}

	@GetMapping(EndpointUtils.ID)
	public ResponseEntity<CompanyResponseDTO> findById(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		var companyResponseDTO = this.companyService.findById(id);

		if (Objects.isNull(companyResponseDTO)) {
			return ResponseEntity.notFound().build();

		} else {
			return ResponseEntity.ok(companyResponseDTO);

		}
	}

	@PostMapping
	public ResponseEntity<CompanyResponseDTO> create(@RequestBody @Valid CompanyRequestDTO companyRequestDTO) {
		var companyResponseDTO = this.companyService.create(companyRequestDTO);

		var localtionResourceURI = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(EndpointUtils.ID)
				.buildAndExpand(companyResponseDTO.getId())
				.toUri();

		return ResponseEntity.created(localtionResourceURI).body(companyResponseDTO);
	}

	@PutMapping(EndpointUtils.ID)
	public ResponseEntity<CompanyResponseDTO> update(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id,
			@RequestBody @Valid CompanyRequestDTO companyRequestDTO) {

		if (this.companyService.exists(id)) {
			var companyResponseDTO = this.companyService.update(id, companyRequestDTO);
			return ResponseEntity.ok(companyResponseDTO);

		} else {
			throw new ObjectNotFoundException(ConstantsUtils.COMPANY_ENTITY, id);
		}
	}

	@DeleteMapping(EndpointUtils.ID)
	public ResponseEntity<Void> delete(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		this.companyService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
