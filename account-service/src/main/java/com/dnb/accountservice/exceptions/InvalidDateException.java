package com.dnb.accountservice.exceptions;

public class InvalidDateException extends Exception {
	public InvalidDateException(String msg) {
		super(msg);
	}

	@Override
	public String toString() {
		return super.toString() + " " + super.getMessage();
	}
}
