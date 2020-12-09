package br.com.bbarreto.api.dto;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "ErrorResponse")
@Data
@Builder
public class ErrorResponseDTO {
	
	@NotEmpty
	private Instant timestamp;
	
	@NotNull
	private int status;
	
	@NotEmpty
	private String error;
	
	@NotEmpty
	private String message;
	
	private String path;
}
