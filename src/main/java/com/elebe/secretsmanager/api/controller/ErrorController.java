package com.elebe.secretsmanager.api.controller;

import com.elebe.secretsmanager.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.secretsmanager.model.InvalidParameterException;
import software.amazon.awssdk.services.secretsmanager.model.ResourceNotFoundException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidParameterException(InvalidParameterException e) {
        ApiResponse<String> response = ApiResponse.failure("Invalid parameter");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiResponse<String> response = ApiResponse.failure("Secret not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AwsServiceException.class)
    public ResponseEntity<ApiResponse<String>> handleAwsServiceException(AwsServiceException e) {
        ApiResponse<String> response = ApiResponse.failure("An error occurred while processing request");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
