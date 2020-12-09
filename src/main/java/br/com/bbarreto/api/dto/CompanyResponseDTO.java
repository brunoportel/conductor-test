package br.com.bbarreto.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.bbarreto.api.model.CompanyModel;
import br.com.bbarreto.api.model.CustomerModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "CompanyResponse")
@Data
public class CompanyResponseDTO implements Serializable {

	private static final long serialVersionUID = -2004880868272334924L;

	private long id;
	
	@NotEmpty
	@Size(min = 2, max = 120)
	private String name;
	
	@Email
	@NotEmpty
	@Size(max = 100)
	private String email;
	
	@NotNull
	private boolean active;
	
	@NotNull
	private OffsetDateTime createDate;
	
	@NotNull
	private OffsetDateTime lastUpdate;
	
	private List<CustomerResponseDTO> customers;
	
	public CompanyResponseDTO(CompanyModel companyModel, Set<CustomerModel> customers) {
		this.setId(companyModel.getId());
		this.setName(companyModel.getName());
		this.setEmail(companyModel.getEmail());
		this.setActive(companyModel.isActive());
		this.setCreateDate(companyModel.getCreateDate());
		this.setLastUpdate(companyModel.getLastUpdate());
		
		if (Objects.nonNull(customers) && !customers.isEmpty()) {
			this.customers = new ArrayList<>();
			customers.forEach(customerModel -> this.customers.add(new CustomerResponseDTO(customerModel)));
		}
	}
}
