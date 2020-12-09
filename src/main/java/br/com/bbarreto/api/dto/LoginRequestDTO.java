package br.com.bbarreto.api.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "LoginRequest")
@Data
public class LoginRequestDTO {

	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
}
