package com.dnb.accountservice.controller;

import java.util.List;

//@Valid is similar to having throwing exceptions in setter
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.payload.request.AccountRequest;
import com.dnb.accountservice.service.AccountService;
import com.dnb.accountservice.utils.RequestToEntityMapper;

import jakarta.validation.Valid;

@RefreshScope
@RestController // RestController derived from controller + response body
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	RequestToEntityMapper mapper;
	
	@Value("${customProperty.test}")
	private String test;
	
	@GetMapping("/test")
	public ResponseEntity<String> getTest() {
		return ResponseEntity.ok(test);
	}

	// insert or create account : post: @PostMapping
	@PostMapping("/create") // @RequestMapping + pose Method ==> spring 4.3.x

	public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest account) {

		Account account2 = mapper.getAccountEntityObject(account);
		try {
			Account account3 = accountService.createAccount(account2);

			return new ResponseEntity(account2, HttpStatus.CREATED);
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		
	}

	@GetMapping("/{accountId}") // It should help us to extract data
	public ResponseEntity<?> getAccountById(@PathVariable("accountId") String accountId) throws IdNotFoundException {
		Optional<Account> optional = accountService.getAccountById(accountId);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			/*
			 * //No need of this commented code //to form the json object in java best
			 * option is to work with map. // Map<String, String> map = new HashMap<>(); //
			 * map.put("Message", "Id not found"); // map.put("HttpStatus",
			 * HttpStatus.NOT_FOUND+""); // ResponseEntity<?> responseEntity = new
			 * ResponseEntity(map,HttpStatus.NOT_FOUND); // //return responseEntity; //
			 * return ResponseEntity.notFound().build();//there will be no msg to understand
			 * the issue
			 */
			throw new IdNotFoundException("Provide proper Id. Since its not present");
		}

	}

	@DeleteMapping("/{accountId}")
	public ResponseEntity<?> deleteAccountById(@PathVariable("accountId") String accountId) throws IdNotFoundException {
		// find existion using
		if (accountService.checkAccountId(accountId)) {
			accountService.deleteAccountById(accountId);
			return ResponseEntity.noContent().build();
		} else
			throw new IdNotFoundException("Provide proper Id. Since its not present");
	}

	@GetMapping("/allAccounts/{contactNumber:^[0-9]{10}$}")
	public ResponseEntity<?> getAllAccountsByContactNumber(@PathVariable("contactNumber") String contactNumber)
			throws IdNotFoundException {
		List<Account> list = accountService.getAccountByContactNumber(contactNumber);
		if (list.size() > 0)
			return ResponseEntity.ok(list);
		else
			throw new IdNotFoundException("Provide proper Contact Number.");
	}

	@GetMapping("/allDetails")
	public ResponseEntity<?> getAllAccounts() throws IdNotFoundException {
		List<Account> list = accountService.getAllAccounts();
		if (list.size() > 0)
			return ResponseEntity.ok(list);
		else
			throw new IdNotFoundException("No details");
	}

//	@GetMapping("/cn/{contactNumber:^[0-9]{10}$}")
//	public ResponseEntity<?> getAccountByContactNumber(@PathVariable("contactNumber") String contactNumber) throws IdNotFoundException{
//		//Optional<Account> optional = accountService.getAccountByContactNumber(contactNumber);
//		if(optional.isPresent()) {
//			return ResponseEntity.ok(optional.get());
//		}else {
//			/*//No need of this commented code
//			 * //to form the json object in java best option is to work with map. //
//			 * Map<String, String> map = new HashMap<>(); // map.put("Message",
//			 * "Id not found"); // map.put("HttpStatus", HttpStatus.NOT_FOUND+""); //
//			 * ResponseEntity<?> responseEntity = new
//			 * ResponseEntity(map,HttpStatus.NOT_FOUND); // //return responseEntity; //
//			 * return ResponseEntity.notFound().build();//there will be no msg to understand
//			 * the issue
//			 */			
//			throw new IdNotFoundException("Provide proper Contact Number.");
//		}
//		
//	}

}


