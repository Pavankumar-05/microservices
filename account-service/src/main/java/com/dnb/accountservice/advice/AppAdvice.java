package com.dnb.accountservice.advice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.dnb.accountservice.exceptions.IdNotFoundException;

@ControllerAdvice
public class AppAdvice {
	//for simple message with status code and message
	//@ResponseStatus(value = HttpStatus.NOT_FOUND,reason="Invalid id provided")
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> IdNotFoundExceptionHandler(IdNotFoundException e) {
		Map<String,String> map = new HashMap<>();
		map.put("Message", e.getMessage());
		map.put("HttpStatus", HttpStatus.NOT_FOUND+"");
		return new ResponseEntity(map,HttpStatus.NOT_FOUND);
}

	
	
	//Method not supported exception should be handled by the creator itself
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String,String>> handleException(HttpRequestMethodNotSupportedException e) throws IOException{
		
		String provided = e.getMethod();
		List<String> supported = List.of(e.getSupportedMethods());
		
		String error = provided
				+" is not one of the supported Http Methods ("
				+String.join(", ", supported)
				+")";
		
		Map<String, String> errorResponse = Map.of(
				"error",error,
				"message",e.getLocalizedMessage(),
				"status", HttpStatus.METHOD_NOT_ALLOWED.toString()
				);
		return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("timestamp", LocalDateTime.now());
		responseBody.put("status", status.value());
		
		List<Object> errors = ex.getBindingResult().getFieldErrors()
		.stream()
		.map(x -> x.getRejectedValue())
		.collect(Collectors.toList());
	
		responseBody.put("errors", errors);
		
		return new ResponseEntity(responseBody, headers, status);
	}
	
	
	
	
	
}
