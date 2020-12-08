package br.com.bbarreto.api.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.bbarreto.api.core.datasource.RoutingDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Value("${core.tenants.file-name}")
	private String coreTenantsFileName;

	@Bean
	public DataSource dataSource() {
		return new RoutingDataSource(this.coreTenantsFileName);
	}
}
