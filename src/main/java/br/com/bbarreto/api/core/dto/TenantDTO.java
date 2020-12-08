package br.com.bbarreto.api.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TenantDTO {
	@JsonProperty
    private String id;
	
	@JsonProperty
    private String driver;
	
	@JsonProperty
    private String url;
	
	@JsonProperty
    private String username;
	
	@JsonProperty
    private String password;
	
	@JsonProperty
    private String testQuery;
	
	@JsonProperty
    private long timeout;
	
	@JsonProperty
    private int minIdle;
	
	@JsonProperty
    private int maxSize;
}
