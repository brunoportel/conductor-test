package br.com.bbarreto.api.core.util;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@ConfigurationProperties(prefix = "database")
@Component
public class DatabaseTenantsPropertiesUtils {

	private List<Tenant> tenants;
	
	@Data
    public static class Tenant {
        private String id;
        private String driver;
        private String url;
        private String username;
        private String password;
        private String testQuery;
        private long timeout;
        private int minIdle;
        private int maxSize;
    }
}
