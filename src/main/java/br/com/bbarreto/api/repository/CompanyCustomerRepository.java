package br.com.bbarreto.api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.bbarreto.api.model.CompanyCustomerModel;
import br.com.bbarreto.api.model.id.CompanyCustomerId;

@Repository
public interface CompanyCustomerRepository extends CrudRepository<CompanyCustomerModel, CompanyCustomerId> {

	@Modifying
	@Query("delete from CompanyCustomerModel c where c.companyCustomerId.companyId = :companyId")
	void deleteByCompanyId(Long companyId);
}
