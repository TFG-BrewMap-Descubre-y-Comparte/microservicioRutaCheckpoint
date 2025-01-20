package com.project.routeCheckpoint.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.routeCheckpoint.exceptions.ExceptionNotValidData;
import com.project.routeCheckpoint.exceptions.ExceptionRoutNotFound;
import com.project.routeCheckpoint.persistance.models.ApiError;

@RestControllerAdvice
public class GlobalControllerError {
	
	@ExceptionHandler(ExceptionNotValidData.class)
	 public ResponseEntity<ApiError> notValidData(ExceptionNotValidData e) {
	     ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
	     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	 @ExceptionHandler(ExceptionRoutNotFound.class)
	 public ResponseEntity<ApiError> handleRouteNotFoundException(ExceptionRoutNotFound e) {
	      ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
	      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	 }

}
