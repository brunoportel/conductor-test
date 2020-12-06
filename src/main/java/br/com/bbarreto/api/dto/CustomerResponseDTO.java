package br.com.bbarreto.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import br.com.bbarreto.api.model.CustomerModel;
import lombok.Data;

@Data
public class CustomerResponseDTO implements Serializable {

	private static final long serialVersionUID = -8462952035451136971L;
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean active;
	private OffsetDateTime createDate;
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
