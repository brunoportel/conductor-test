package br.com.bbarreto.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.bbarreto.api.model.CustomerModel;
import lombok.Data;

@Data
public class CustomerRequestDTO implements Serializable {
	
	private static final long serialVersionUID = -9220165763399034454L;
	
	@NotEmpty
	@Size(min = 2, max = 50)
	private String firstName;
	
	@NotEmpty
	@Size(min = 2, max = 50)
	private String lastName;
	
	@NotEmpty
	@Email
	@Size(max = 100)
	private String email;

	public CustomerModel toModel() {
		return CustomerModel.builder()
		.firstName(this.getFirstName())
		.lastName(this.getLastName())
		.email(this.getEmail())
		.build();
	}
}
