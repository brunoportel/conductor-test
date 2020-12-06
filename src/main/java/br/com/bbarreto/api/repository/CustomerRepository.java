package br.com.bbarreto.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.bbarreto.api.model.CustomerModel;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerModel, Long> {

	@Override
	List<CustomerModel> findAll();
}
