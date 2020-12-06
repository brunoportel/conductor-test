package br.com.bbarreto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bbarreto.api.core.service.JwtService;
import br.com.bbarreto.api.dto.LoginRequestDTO;
import br.com.bbarreto.api.dto.LoginResponseDTO;
import br.com.bbarreto.api.util.EndpointUtils;

@RestController
@RequestMapping(EndpointUtils.AUTH_V1)
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@PostMapping
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		var authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		var loginResponseDTO = LoginResponseDTO.builder()
				.token(this.jwtService.createFromAuthentication(authentication))
				.build();

		return ResponseEntity.ok(loginResponseDTO);
	}
}
