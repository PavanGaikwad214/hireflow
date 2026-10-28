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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException exception){

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ApiErrorResponse response = new ApiErrorResponse(
                400,
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

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCompanyNotFoundException(CompanyNotFoundException exception){

        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RecruiterProfileNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleRecruiterProfileNotFoundException(
            RecruiterProfileNotFoundException exception
    ){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleJobNotFoundException(JobNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(JobAccessDeniedException.class)
    public  ResponseEntity<ApiErrorResponse> handleJobAccessDeniedException(JobAccessDeniedException exception){

        ApiErrorResponse response = new ApiErrorResponse(
                403,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(JobAlreadyClosedException.class)
    public ResponseEntity<ApiErrorResponse> handleJobAlreadyClosedException(JobAlreadyClosedException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ApplicationClosedException.class)
    public ResponseEntity<ApiErrorResponse> handleApplicationClosedException(ApplicationClosedException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ApplicationAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleApplicationAlreadyExistsException(ApplicationAlreadyExistsException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(JobClosedException.class)
    public ResponseEntity<ApiErrorResponse> handleJobClosedException(JobClosedException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApplicationNotFoundException(ApplicationNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidApplicationStatusTransitionException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidApplicationStatusTransitionException(InvalidApplicationStatusTransitionException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EducationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEducationNotFoundException(EducationNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ExperienceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleExperienceNotFoundException(ExperienceNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProjectNotFoundException(ProjectNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ResumeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResumeNotFoundException(ResumeNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CertificationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCertificationNotFoundException(CertificationNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(JobAlreadySavedException.class)
    public ResponseEntity<ApiErrorResponse> handleJobAlreadySavedException(JobAlreadySavedException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(SavedJobNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleSavedJobNotFoundException(SavedJobNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RecruiterNoteNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleRecruiterNoteNotFoundException(RecruiterNoteNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InterviewAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleInterviewAlreadyExistsException(InterviewAlreadyExistsException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                409,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InterviewNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleInterviewNotFoundException(InterviewNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidSalaryRangeException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidSalaryRangeException(InvalidSalaryRangeException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                400,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NewPasswordMustBeDifferentException.class)
    public ResponseEntity<ApiErrorResponse> handleNewPasswordMustBeDifferentException(NewPasswordMustBeDifferentException exception){
        ApiErrorResponse response = new ApiErrorResponse(
                400,
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
