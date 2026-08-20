package com.pavan.hireflow.common.exception;

import com.pavan.hireflow.common.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Map<String, String> errors = new HashMap<>();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException exception){

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                "Validation failed",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException exception){

        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException exception){

        ApiErrorResponse response = new ApiErrorResponse(
                401,
                exception.getMessage(),
                LocalDateTime.now(),
                null

        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccountInactiveException.class)
    public ResponseEntity<ApiErrorResponse> handleAccountDisabledException(AccountInactiveException exception){

        ApiErrorResponse response = new ApiErrorResponse(
                403,
                exception.getMessage(),
                LocalDateTime.now(),
                null

        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(CandidateProfileNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCandidateProfileNotFoundException(CandidateProfileNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CandidateProfileAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleCandidateProfileAlreadyExistException(CandidateProfileAlreadyExistsException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleSkillNotFoundException(SkillNotFoundException exception){

        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
