package br.com.bbarreto.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.bbarreto.api.model.CustomerModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "CustomerResponse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO implements Serializable {

	private static final long serialVersionUID = -8462952035451136971L;
	
	@NotNull
	private long id;
	
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
	
	@NotNull
	private boolean active;
	
	@NotNull
	private OffsetDateTime createDate;
	
	@NotNull
	private OffsetDateTime lastUpdate;
	
	public CustomerResponseDTO(CustomerModel customerModel) {
		this.setId(customerModel.getId());
		this.setFirstName(customerModel.getFirstName());
		this.setLastName(customerModel.getLastName());
		this.setEmail(customerModel.getEmail());
		this.setActive(customerModel.isActive());
		this.setCreateDate(customerModel.getCreateDate());
		this.setLastUpdate(customerModel.getLastUpdate());
	}
}
