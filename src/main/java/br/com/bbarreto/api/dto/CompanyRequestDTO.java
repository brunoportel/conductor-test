package br.com.bbarreto.api.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.bbarreto.api.model.CompanyModel;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CompanyRequest")
@Data
public class CompanyRequestDTO implements Serializable {

	private static final long serialVersionUID = 9211905465986223114L;
	
	@NotEmpty
	@Size(min = 2, max = 120)
	private String name;
	
	@Email
	@NotEmpty
	@Size(max = 100)
	private String email;
	
	private Set<IdDTO> customers;
	
	public CompanyModel toModel() {
		var companyModel = new CompanyModel();
		companyModel.setName(this.getName());
		companyModel.setEmail(this.getEmail());
		return companyModel;
	}
}
