package br.com.bbarreto.api.util;

public class ConstantsUtils {

	private ConstantsUtils() {
		throw new IllegalStateException("ConstantsUtils utility class");
	}

	public static final String CUSTOMER_ENTITY = "Customer";
	public static final String COMPANY_ENTITY = "Company";

	public static final String OBJECT_NOT_FOUND_TEMPLATE = "{0} ({1}) not found";

	public static final String MESSAGES_FILE_LOCATION = "classpath:messages";

	public static final String UNAUTHORIZED = "Error on process request: unauthorized";
}
