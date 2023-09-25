//package com.dnb.accountservice.service;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import com.dnb.accountservice.dto.Customer;
//
//@FeignClient(value = "CUSTOMER-SERVICE",url = "http://localhost:9010")
//public interface APIClient {
//	@GetMapping(value = "/api/customer/{id}")
//	Customer getCustomerById(@PathVariable("id") int customerId);
//}
