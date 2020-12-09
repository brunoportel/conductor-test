package br.com.bbarreto.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.bbarreto.api.model.CustomerModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "CustomerRequest")
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
		var customerModel = new CustomerModel();
		customerModel.setFirstName(this.getFirstName());
		customerModel.setLastName(this.getLastName());
		customerModel.setEmail(this.getEmail());
		return customerModel;
	}
}
