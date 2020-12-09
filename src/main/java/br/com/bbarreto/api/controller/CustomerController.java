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
import br.com.bbarreto.api.dto.CustomerRequestDTO;
import br.com.bbarreto.api.dto.CustomerResponseDTO;
import br.com.bbarreto.api.dto.ListResponse;
import br.com.bbarreto.api.service.CustomerService;
import br.com.bbarreto.api.util.EndpointUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer", description = "Customer API")
@RestController
@RequestMapping(EndpointUtils.CUSTOMER_V1)
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "List all customers", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@GetMapping
	public ResponseEntity<ListResponse<CustomerResponseDTO>> findAll() {
		return ResponseEntity.ok(new ListResponse<>(this.customerService.findAll()));
	}

	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Get a customer by Id", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@GetMapping(EndpointUtils.ID)
	public ResponseEntity<CustomerResponseDTO> findById(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		var customerResponseDTO = this.customerService.findById(id);

		if (Objects.nonNull(customerResponseDTO)) {
			return ResponseEntity.ok(customerResponseDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(summary = "Create a new customer", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@PostMapping
	public ResponseEntity<CustomerResponseDTO> create(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
		var customerResponseDTO = this.customerService.create(customerRequestDTO);

		var localtionResourceURI = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(EndpointUtils.ID)
				.buildAndExpand(customerResponseDTO.getId())
				.toUri();

		return ResponseEntity.created(localtionResourceURI).body(customerResponseDTO);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Update a existing customer", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@PutMapping(EndpointUtils.ID)
	public ResponseEntity<CustomerResponseDTO> update(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id,
			@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {

		ResponseEntity<CustomerResponseDTO> responseEntity = null;

		if (this.customerService.exists(id)) {
			var customerResponseDTO = this.customerService.update(id, customerRequestDTO);

			if (Objects.nonNull(customerResponseDTO)) {
				responseEntity = ResponseEntity.ok(customerResponseDTO);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
		} else {
			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a customer", security = @SecurityRequirement(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME))
	@DeleteMapping(EndpointUtils.ID)
	public ResponseEntity<Void> delete(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		this.customerService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
