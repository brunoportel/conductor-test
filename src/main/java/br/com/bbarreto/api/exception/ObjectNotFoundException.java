package br.com.bbarreto.api.exception;

import java.text.MessageFormat;

import br.com.bbarreto.api.util.ConstantsUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5668113541903567945L;
	private String objectType;
	private transient Object objectKey;

	public ObjectNotFoundException(String objectType, Object objectKey) {
		super(MessageFormat.format(ConstantsUtils.OBJECT_NOT_FOUND_TEMPLATE, objectType, objectKey));
	}
}
