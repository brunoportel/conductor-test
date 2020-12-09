package br.com.bbarreto.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.bbarreto.api.model.CompanyModel;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyModel, Long> {

	@Override
	List<CompanyModel> findAll();
}
