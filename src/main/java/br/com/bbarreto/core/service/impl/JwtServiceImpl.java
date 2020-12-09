package br.com.bbarreto.core.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.bbarreto.core.dto.ApiUserDetailsDTO;
import br.com.bbarreto.core.service.JwtService;
import br.com.bbarreto.core.util.CoreConstantsUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JwtServiceImpl implements JwtService {

	@Value("${core.security.jwt.expiration}")
	private long expiration;

	@Value("${core.security.jwt.expiration-limit}")
	private long expirationLimit;

	@Value("${core.security.jwt.secret}")
	private String secretKey;

	@Value("${core.security.jwt.issuer}")
	private String issuer;

	@Override
	public String createFromAuthentication(Authentication authentication) {

		var apiUserDetailsDTO = (ApiUserDetailsDTO) authentication.getPrincipal();

		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, this.secretKey)
				.setExpiration(this.createExpirationTime())
				.setIssuer(this.issuer)
				.setSubject((apiUserDetailsDTO.getUsername()))
				.setIssuedAt(new Date())
				.compact();
	}

	@Override
	public String getUserName(String jwtToken) {
		return Jwts.parser()
				.setSigningKey(this.secretKey)
				.parseClaimsJws(jwtToken)
				.getBody()
				.getSubject();
	}

	@Override
	public boolean isValid(String jwtToken) {
		var isValid = false;

		try {
			if (StringUtils.isBlank(jwtToken)) {
				throw new IllegalArgumentException("jwtToken cannot be null");
			}

			Jwts.parser()
			.setSigningKey(this.secretKey)
			.setAllowedClockSkewSeconds(this.expirationLimit)
			.parseClaimsJws(jwtToken);

			isValid = true;
		} catch (Exception e) {
			JwtServiceImpl.log.error(CoreConstantsUtils.CHECK_JWT_TOKEN_ERROR, e.getMessage());
		}

		return isValid;
	}

	private Date createExpirationTime() {
		return new Date(System.currentTimeMillis() + this.expiration);
	}

	@Override
	public String extractFromAuthorizationHeader(HttpServletRequest httpServletRequest) {
		var jwtToken = StringUtils.EMPTY;
		var authorizationHeader = httpServletRequest.getHeader(CoreConstantsUtils.AUTHORIZATION_HEADER_NAME);

		if (StringUtils.isNotBlank(authorizationHeader) && StringUtils.startsWithIgnoreCase(authorizationHeader,
				CoreConstantsUtils.AUTHORIZATION_BAERER_PREFIX)) {
			jwtToken = StringUtils.replaceOnceIgnoreCase(authorizationHeader,
					CoreConstantsUtils.AUTHORIZATION_BAERER_PREFIX, StringUtils.EMPTY);
		}

		return jwtToken;
	}
}
