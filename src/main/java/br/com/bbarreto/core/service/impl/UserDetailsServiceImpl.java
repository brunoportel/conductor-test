package br.com.bbarreto.core.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bbarreto.core.entrypoint.ApiUserDetails;
import br.com.bbarreto.core.util.CoreConstantsUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public ApiUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Just an example to simulate a db call
		return this.loadUserFromDatabase().stream()
				.filter(user -> user.getUsername().equals(username))
				.findAny()
				.orElseThrow(() -> new UsernameNotFoundException(
						MessageFormat.format(CoreConstantsUtils.USER_NOT_FOUND_TEMPLATE, username)));
	}

	private List<ApiUserDetails> loadUserFromDatabase() {
		var apiUserDetailsList = new ArrayList<ApiUserDetails>(2);

		apiUserDetailsList.add(
				ApiUserDetails.builder()
				.username("user1")
				.password("$2a$10$9zknNKfuwydXd5PFp7pdx./9deDWzzSQNqCOapHAMcYKC.hM21g/S")
				.authorities(Arrays.asList(new SimpleGrantedAuthority("USER")))
				.tenantId("tenantId1")
				.build());

		apiUserDetailsList.add(
				ApiUserDetails.builder()
				.username("user2")
				.password("$2a$10$YqE2DgVo7455gW6X0jvFWeFWRSp0fcz7yCccIHPH1N9n8E73CHmyy")
				.authorities(Arrays.asList(new SimpleGrantedAuthority("USER")))
				.tenantId("tenantId2")
				.build());

		return apiUserDetailsList;
	}
}
