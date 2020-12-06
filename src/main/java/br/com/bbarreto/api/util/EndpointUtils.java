package br.com.bbarreto.api.util;

public class EndpointUtils {

	private EndpointUtils() {
		throw new IllegalStateException("EndpointUtils utility class");
	}

	public static final String ID = "/{id}";
	public static final String AUTH_V1 = "/v1/auth";
	public static final String CUSTOMER_V1 = "/v1/customer";
	public static final String COMPANY_V1 = "/v1/company";

	public static final String ID_PATH_VAR = "id";
}
