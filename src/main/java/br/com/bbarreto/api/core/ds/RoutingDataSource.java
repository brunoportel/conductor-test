package br.com.bbarreto.api.core.ds;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.bbarreto.api.core.entrypoint.ApiUserDetails;

public class RoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		var currentLookupKey = StringUtils.EMPTY;

		var authentication = SecurityContextHolder.getContext().getAuthentication();

		if (Objects.nonNull(authentication)) {
			var apiUserDetails = (ApiUserDetails) authentication.getPrincipal();

			if (Objects.nonNull(apiUserDetails) && StringUtils.isNotBlank(apiUserDetails.getTenantId())) {
				currentLookupKey = apiUserDetails.getTenantId();
			}
		}

		return currentLookupKey;
	}
}
