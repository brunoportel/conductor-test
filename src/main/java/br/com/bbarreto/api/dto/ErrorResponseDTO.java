package br.com.bbarreto.api.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
	private Instant timestamp;
	private int status;
	private String error;
	private String message;
}
