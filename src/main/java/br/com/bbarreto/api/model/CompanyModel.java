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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
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
@Table(name = "tb_company")
public class CompanyModel implements Serializable {

	private static final long serialVersionUID = -2287560298926193347L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private long id;

	@Column(name = "name")
	private String name;

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
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_company_customer",
            joinColumns = {
                    @JoinColumn(name = "company_id", referencedColumnName = "company_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id",
                            nullable = false, updatable = false)})
    private Set<CustomerModel> customers = new HashSet<>();
}
