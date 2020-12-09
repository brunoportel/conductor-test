package br.com.bbarreto.api.controller;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.bbarreto.api.core.util.CoreConstantsUtils;
import br.com.bbarreto.api.dto.CompanyRequestDTO;
import br.com.bbarreto.api.dto.CompanyResponseDTO;
import br.com.bbarreto.api.dto.ListResponse;
import br.com.bbarreto.api.service.CompanyService;
import br.com.bbarreto.api.util.EndpointUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Company", description = "Company API")
@RestController
@RequestMapping(EndpointUtils.COMPANY_V1)
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "List all companies", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@GetMapping
	public ResponseEntity<ListResponse<CompanyResponseDTO>> findAll() {
		return ResponseEntity.ok(new ListResponse<>(this.companyService.findAll()));
	}

	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Get a company by Id", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping(EndpointUtils.ID)
	public ResponseEntity<CompanyResponseDTO> findById(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		var companyResponseDTO = this.companyService.findById(id);

		if (Objects.nonNull(companyResponseDTO)) {
			return ResponseEntity.ok(companyResponseDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(summary = "Create a new company", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@PostMapping
	public ResponseEntity<CompanyResponseDTO> create(@RequestBody @Valid CompanyRequestDTO companyRequestDTO) {
		var companyId = this.companyService.create(companyRequestDTO);
		var companyResponseDTO = this.companyService.findById(companyId);

		var localtionResourceURI = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(EndpointUtils.ID)
				.buildAndExpand(companyId)
				.toUri();

		return ResponseEntity.created(localtionResourceURI).body(companyResponseDTO);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Update an existing company", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@PutMapping(EndpointUtils.ID)
	public ResponseEntity<CompanyResponseDTO> update(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id,
			@RequestBody @Valid CompanyRequestDTO companyRequestDTO) {

		ResponseEntity<CompanyResponseDTO> responseEntity = null;

		if (this.companyService.exists(id)) {
			this.companyService.update(id, companyRequestDTO);
			var companyResponseDTO = this.companyService.findById(id);

			if (Objects.nonNull(companyResponseDTO)) {
				responseEntity = ResponseEntity.ok(companyResponseDTO);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
		} else {
			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a company", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@DeleteMapping(EndpointUtils.ID)
	public ResponseEntity<Void> delete(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		this.companyService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
