package br.com.bbarreto.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.bbarreto.core.entrypoint.ApiAuthenticationEntryPoint;
import br.com.bbarreto.core.filter.AuthenticateFilter;
import br.com.bbarreto.core.service.impl.UserDetailsServiceImpl;

@Order(1)
@Profile("test")
@Configuration
public class WebSecurityConfigTest extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	public AuthenticateFilter authenticateFilter;

	@Autowired
	public ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService);
		auth.inMemoryAuthentication()
		.passwordEncoder(this.passwordEncoder)
		.withUser("user1")
		.password(this.passwordEncoder.encode("passwordforuser1"))
		.roles("USER");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(this.apiAuthenticationEntryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		.antMatchers("/v1/auth/**", "/actuator/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
		.permitAll().anyRequest().authenticated();

		httpSecurity.addFilterBefore(this.authenticateFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
