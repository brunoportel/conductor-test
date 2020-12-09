package br.com.bbarreto.core.datasource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;

import br.com.bbarreto.core.dto.TenantDTO;
import br.com.bbarreto.core.dto.TenantListDTO;
import br.com.bbarreto.core.entrypoint.ApiUserDetails;
import br.com.bbarreto.core.util.CoreConstantsUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoutingDataSource extends AbstractRoutingDataSource {

	private final String filename;
	private final ConcurrentMap<String, HikariDataSource> tenantsConcurrentMap;

	public RoutingDataSource(String filename) {
		this.filename = filename;
		this.tenantsConcurrentMap = this.getDataSources();
	}

	@Override
	public void afterPropertiesSet() {
		// Do nothing
	}

	@Override
	protected DataSource determineTargetDataSource() {
		var lookupKey = (String) this.determineCurrentLookupKey();
		return this.tenantsConcurrentMap.get(lookupKey);
	}

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

	private ConcurrentMap<String, HikariDataSource> getDataSources() {
		var configurations = this.getTenantsFromConfiguration();
		return configurations.stream().collect(Collectors.toConcurrentMap(TenantDTO::getId, this::buildDataSource));
	}

	private List<TenantDTO> getTenantsFromConfiguration() {
		try {
			var properties = new ObjectMapper().readValue(Files.readString(Paths.get(this.filename)),
					TenantListDTO.class);
			return properties.getTenants();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private HikariDataSource buildDataSource(TenantDTO configuration) {
		var hikariDataSource = new HikariDataSource();

		hikariDataSource.setJdbcUrl(configuration.getUrl());
		hikariDataSource.setUsername(configuration.getUsername());
		hikariDataSource.setPassword(configuration.getPassword());
		hikariDataSource.setDriverClassName(configuration.getDriver());
		hikariDataSource.setConnectionInitSql(configuration.getTestQuery());
		hikariDataSource.setConnectionTestQuery(configuration.getTestQuery());
		hikariDataSource.setConnectionTimeout(configuration.getTimeout());
		hikariDataSource.setInitializationFailTimeout(0);
		hikariDataSource.setMinimumIdle(configuration.getMinIdle());
		hikariDataSource.setMaximumPoolSize(configuration.getMaxSize());

		return hikariDataSource;
	}

	@Scheduled(fixedDelay = 30000L)
	public void refreshDataSources() {
		var configurations = this.getTenantsFromConfiguration();

		this.removeObsoleteTenants(configurations);
		this.insertOrUpdateTenants(configurations);
	}

	private void insertOrUpdateTenants(List<TenantDTO> tenants) {

		tenants.forEach(configuration -> {
			if (this.tenantsConcurrentMap.containsKey(configuration.getId())) {
				var dataSource = this.tenantsConcurrentMap.get(configuration.getId());
				if (!this.isCurrentConfiguration(dataSource, configuration)) {
					dataSource.close();
					RoutingDataSource.log.info(CoreConstantsUtils.ROUTING_UPDATING_DATASOURCE_TEMPLATE,
							configuration.getId());
					this.tenantsConcurrentMap.put(configuration.getId(), this.buildDataSource(configuration));
				}
			} else {
				RoutingDataSource.log.info(CoreConstantsUtils.ROUTING_CREATING_DATASOURCE_TEMPLATE,
						configuration.getId());
				this.tenantsConcurrentMap.put(configuration.getId(), this.buildDataSource(configuration));
			}
		});
	}

	private void removeObsoleteTenants(List<TenantDTO> configurations) {
		var tenantNamesFromConfiguration = configurations.stream().map(TenantDTO::getId)
				.collect(Collectors.toSet());

		var keysToRemove = new HashSet<String>();

		this.tenantsConcurrentMap.keySet().forEach(tenantId -> {
			if (!tenantNamesFromConfiguration.contains(tenantId)) {
				var dataSource = this.tenantsConcurrentMap.get(tenantId);
				dataSource.close();
				keysToRemove.add(tenantId);
			}
		});

		keysToRemove.forEach(keyToRemove -> {
			RoutingDataSource.log.info(CoreConstantsUtils.ROUTING_REMOVE_DATASOURCE_TEMPLATE, keyToRemove);
			this.tenantsConcurrentMap.remove(keyToRemove);
		});
	}

	private boolean isCurrentConfiguration(HikariDataSource dataSource, TenantDTO configuration) {
		return Objects.equals(dataSource.getJdbcUrl(), configuration.getUrl())
				&& Objects.equals(dataSource.getUsername(), configuration.getUsername())
				&& Objects.equals(dataSource.getPassword(), configuration.getPassword())
				&& Objects.equals(dataSource.getDriverClassName(), configuration.getDriver())
				&& dataSource.getMinimumIdle() == configuration.getMinIdle()
				&& dataSource.getMaximumPoolSize() == configuration.getMaxSize()
				&& dataSource.getConnectionTimeout() == configuration.getTimeout()
				&& Objects.equals(dataSource.getConnectionTestQuery(), configuration.getTestQuery());
	}
}
