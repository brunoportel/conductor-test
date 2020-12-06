package br.com.bbarreto.api.core.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import br.com.bbarreto.api.util.ConstantsUtils;

@Configuration
public class MessageSourceConfig {

	@Bean
	public MessageSource messageSource() {
		var messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(ConstantsUtils.MESSAGES_FILE_LOCATION);
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		return messageSource;
	}
}
