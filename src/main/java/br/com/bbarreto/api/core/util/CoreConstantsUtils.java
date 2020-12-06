package br.com.bbarreto.api.core.util;

public class CoreConstantsUtils {

	private CoreConstantsUtils() {
		throw new IllegalStateException("CoreConstantsUtils utility class");
	}

	public static final String TENANT_ID_NAME = "tenantId";
	public static final String CHECK_JWT_TOKEN_ERROR = "Error on check jwt token";
	public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
	public static final String AUTHORIZATION_BAERER_PREFIX = "Bearer ";
	public static final String AUTHORIZATION_HEADER_NOT_PRESENT = "Authorization header not present";
	public static final String READ_JWT_TOKEN_DETAILS = "Error on check jwt token details";
	public static final String USER_NOT_FOUND_TEMPLATE = "Cannot find user with name {0}";
}
