package br.com.bbarreto.api.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.bbarreto.api.model.id.CompanyCustomerId;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_company_customer")
public class CompanyCustomerModel implements Serializable {

	private static final long serialVersionUID = 1109165767962505640L;
	
	@EmbeddedId
	private CompanyCustomerId companyCustomerId;

}
