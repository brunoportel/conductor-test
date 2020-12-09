package br.com.bbarreto.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_customer")
public class CustomerModel implements Serializable {

	private static final long serialVersionUID = -8243628469807032686L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id", updatable = false)
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "active")
	private boolean active;

	@Column(name = "create_date", updatable = false)
	private OffsetDateTime createDate;

	@Column(name = "last_update")
	private OffsetDateTime lastUpdate;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", fetch = FetchType.LAZY)
	private Set<CompanyModel> companies = new HashSet<>();
	
	public CustomerModel(long id) {
		this.id = id;
	}
}
