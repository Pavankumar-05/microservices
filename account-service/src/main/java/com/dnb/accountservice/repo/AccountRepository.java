package com.dnb.accountservice.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dnb.accountservice.dto.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String>{

	public List<Account> findByContactNumber(String contactNumber);
	
}
