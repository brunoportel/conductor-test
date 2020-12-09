package br.com.bbarreto.api.model.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCustomerId implements Serializable {

	private static final long serialVersionUID = 6049862279716103686L;
	
	@Column(name = "company_id")
	private long companyId;
	
	@Column(name = "customer_id")
	private long customerId;
}
