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

import br.com.bbarreto.api.dto.CustomerRequestDTO;
import br.com.bbarreto.api.dto.CustomerResponseDTO;
import br.com.bbarreto.api.dto.ListResponse;
import br.com.bbarreto.api.exception.ObjectNotFoundException;
import br.com.bbarreto.api.service.CustomerService;
import br.com.bbarreto.api.util.ConstantsUtils;
import br.com.bbarreto.api.util.EndpointUtils;

@RestController
@RequestMapping(EndpointUtils.CUSTOMER_V1)
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public ResponseEntity<ListResponse<CustomerResponseDTO>> findAll() {
		return ResponseEntity.ok(new ListResponse<>(this.customerService.findAll()));
	}

	@GetMapping(EndpointUtils.ID)
	public ResponseEntity<CustomerResponseDTO> findById(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		var customerResponseDTO = this.customerService.findById(id);

		if (Objects.isNull(customerResponseDTO)) {
			return ResponseEntity.notFound().build();

		} else {
			return ResponseEntity.ok(customerResponseDTO);

		}
	}

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

	@PutMapping(EndpointUtils.ID)
	public ResponseEntity<CustomerResponseDTO> update(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id,
			@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {

		if (this.customerService.exists(id)) {
			var customerResponseDTO = this.customerService.update(id, customerRequestDTO);
			return ResponseEntity.ok(customerResponseDTO);

		} else {
			throw new ObjectNotFoundException(ConstantsUtils.CUSTOMER_ENTITY, id);
		}
	}

	@DeleteMapping(EndpointUtils.ID)
	public ResponseEntity<Void> delete(@PathVariable(EndpointUtils.ID_PATH_VAR) @Min(1) Long id) {
		this.customerService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
