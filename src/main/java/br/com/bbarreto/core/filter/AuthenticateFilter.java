package br.com.bbarreto.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.bbarreto.core.dto.ApiUserDetailsDTO;
import br.com.bbarreto.core.service.JwtService;
import br.com.bbarreto.core.util.CoreConstantsUtils;

public class AuthenticateFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			var jwtToken = this.jwtService.extractFromAuthorizationHeader(request);

			if (this.jwtService.isValid(jwtToken)) {
				var username = this.jwtService.getUserName(jwtToken);
				var apiUserDetailsDTO = (ApiUserDetailsDTO) this.userDetailsService.loadUserByUsername(username);

				var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(apiUserDetailsDTO, null,
						apiUserDetailsDTO.getAuthorities());

				usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}

		} catch (Exception e) {
			this.logger.error(CoreConstantsUtils.READ_JWT_TOKEN_DETAILS, e);
		}

		filterChain.doFilter(request, response);
	}
}
