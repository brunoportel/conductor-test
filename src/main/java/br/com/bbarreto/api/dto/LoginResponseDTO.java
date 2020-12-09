package br.com.bbarreto.api.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "LoginResponse")
@Data
@Builder
public class LoginResponseDTO {

	@NotEmpty
	private String token;
}
