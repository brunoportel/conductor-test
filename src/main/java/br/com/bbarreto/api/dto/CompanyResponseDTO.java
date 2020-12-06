package br.com.bbarreto.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import br.com.bbarreto.api.model.CompanyModel;
import lombok.Data;

@Data
public class CompanyResponseDTO implements Serializable {

	private static final long serialVersionUID = -2004880868272334924L;

	private long id;
	private String name;
	private String email;
	private boolean active;
	private OffsetDateTime createDate;
	private OffsetDateTime lastUpdate;
	
	public CompanyResponseDTO(CompanyModel companyModel) {
		this.setId(companyModel.getId());
		this.setName(companyModel.getName());
		this.setEmail(companyModel.getEmail());
		this.setActive(companyModel.isActive());
		this.setCreateDate(companyModel.getCreateDate());
		this.setLastUpdate(companyModel.getLastUpdate());
	}
}
