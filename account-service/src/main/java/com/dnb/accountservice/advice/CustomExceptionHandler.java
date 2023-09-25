package com.dnb.accountservice.advice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
//Will be used in case of handling predefined exceptions in spring
//	and app advice for custom and predefined

	
	@Override
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
