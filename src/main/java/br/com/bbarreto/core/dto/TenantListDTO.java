package br.com.bbarreto.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TenantListDTO {

	@JsonProperty
	private List<TenantDTO> tenants;
}
