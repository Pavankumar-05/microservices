package com.dnb.accountservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.dto.Customer;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.repo.AccountRepository;


@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Value("${api.customer}")
	private String URL;
//	@Autowired
//	APIClient apiClient;

	@Override
	public Account createAccount(Account account) throws IdNotFoundException{
		/*For RestTemplate */
		System.out.println(URL);
		try {
			ResponseEntity<Customer> responseEntity = restTemplate.getForEntity(URL+"/" + account.getCustomerId() ,Customer.class);
			Customer customer = responseEntity.getBody();
			System.out.println(customer);
			return accountRepository.save(account);
			
		}
		catch(Exception e) {
			
			System.out.println(e.getMessage());
			throw new IdNotFoundException("Id not found");
			
		}
		//Using FeignClient
//		try {
//			Customer customer = apiClient.getCustomerById(account.getCustomerId());
//			return accountRepository.save(account);
//		}
//		catch(Exception e) {
//			System.out.println(e.getMessage());
//			throw new IdNotFoundException("Id not found");
//		}
		
	}

	@Override
	public Optional<Account> getAccountById(String accountId) {
		return accountRepository.findById(accountId);
	}

	@Override
	public boolean deleteAccountById(String accountId) throws IdNotFoundException {
		boolean isPresent = accountRepository.existsById(accountId);
		if(isPresent) {
			accountRepository.deleteById(accountId);
			
		}else {
			throw new IdNotFoundException("Id not Found");
		}
		if(accountRepository.existsById(accountId))
			return false;
		else
			return true;
		 
	}

	@Override
	public List<Account> getAllAccounts() {
		return (List<Account>) accountRepository.findAll();
	}
	
	@Override
	public boolean checkAccountId(String accountId) {
		if(accountRepository.existsById(accountId))
			return true;
		else
			return false;
	}
	
	@Override
	public List<Account> getAccountByContactNumber(String contactNumber) {
		return  accountRepository.findByContactNumber(contactNumber);
	}
	
}

//To work with openfeign
//Add starter
//Mark annotation called @OpenFeignClient

//From consumer side
/*
URL,i/p,o/p,status code,error handling*/
