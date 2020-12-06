package br.com.bbarreto.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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
}
