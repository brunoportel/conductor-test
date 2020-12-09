package br.com.bbarreto.core.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface JwtService {

	String createFromAuthentication(Authentication authentication);

	String getUserName(String jwtToken);

	boolean isValid(String jwtToken);

	String extractFromAuthorizationHeader(HttpServletRequest httpServletRequest);
}
