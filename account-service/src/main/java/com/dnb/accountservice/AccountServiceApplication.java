package com.dnb.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@EnableDiscoveryClient//There is no differenece whether we add or not this annotation
@SpringBootApplication
//@EnableFeignClients
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
	@Bean
	@LoadBalanced
	//@Scope("prototype")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
