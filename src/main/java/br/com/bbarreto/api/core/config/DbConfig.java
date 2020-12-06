package br.com.bbarreto.api.core.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import br.com.bbarreto.api.core.ds.RoutingDataSource;
import br.com.bbarreto.api.core.util.DatabaseTenantsPropertiesUtils;
import br.com.bbarreto.api.core.util.DatabaseTenantsPropertiesUtils.Tenant;

@Configuration
@EnableTransactionManagement
public class DbConfig {

	@Autowired
	private DatabaseTenantsPropertiesUtils databaseTenantsPropertiesUtils;

	@Bean
	public DataSource dataSource() {
		AbstractRoutingDataSource dataSource = new RoutingDataSource();
		dataSource.setTargetDataSources(this.createTargetDataSources());
		dataSource.afterPropertiesSet();
		return dataSource;
	}

	private Map<Object, Object> createTargetDataSources() {
		Map<Object, Object> targetDataSources = new HashMap<>();

		this.databaseTenantsPropertiesUtils.getTenants().forEach(tenant ->
			targetDataSources.put(tenant.getId(), this.createHikatiPool(tenant))
		);

		return targetDataSources;
	}

	private HikariDataSource createHikatiPool(Tenant tenant) {
		var dataSource = DataSourceBuilder.create()
				.driverClassName(tenant.getDriver())
				.url(tenant.getUrl())
				.username(tenant.getUsername())
				.password(tenant.getPassword())
				.build();

		var hikariDataSource = new HikariDataSource();
		hikariDataSource.setDataSource(dataSource);
		hikariDataSource.setConnectionInitSql(tenant.getTestQuery());
		hikariDataSource.setConnectionTestQuery(tenant.getTestQuery());
		hikariDataSource.setConnectionTimeout(tenant.getTimeout());
		hikariDataSource.setInitializationFailTimeout(0);
		hikariDataSource.setMinimumIdle(tenant.getMinIdle());
		hikariDataSource.setMaximumPoolSize(tenant.getMaxSize());
		return hikariDataSource;
	}
}
