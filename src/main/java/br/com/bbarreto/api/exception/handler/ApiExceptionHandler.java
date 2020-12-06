package br.com.bbarreto.api.exception.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.bbarreto.api.dto.ErrorResponseDTO;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		var fieldError = ex.getBindingResult().getFieldError();
		var message = new StringBuilder()
				.append(fieldError.getField())
				.append(StringUtils.SPACE)
				.append(fieldError.getDefaultMessage())
				.toString();

		var errorResponseDTO = ErrorResponseDTO.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(message)
				.build();

		return ResponseEntity.badRequest().body(errorResponseDTO);
	}
}
