package com.dnb.accountservice.payload.request;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class AccountRequest {
	@NotBlank(message = "account holder name should not be blank")
	private String accountHolderName;
	private String accountType;
	@Min(value = 0,message="value should not be negative")
	private float balance;
	@Length(min = 10,max=10)
	@NotBlank(message = "Contact number should not be empty")
	@Pattern(regexp = "^[0-9]{10}$")
	private String contactNumber;
	@NotBlank(message = "Address should not be empty")
	private String address;
	@NotNull(message = "Date should not be null")
	private LocalDate dob;
	private int customerId;
}
