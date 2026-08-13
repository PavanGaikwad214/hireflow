package com.pavan.hireflow.auth.controller;

import com.pavan.hireflow.auth.dto.AuthResponse;
import com.pavan.hireflow.auth.dto.CandidateRegisterRequest;
import com.pavan.hireflow.auth.dto.LoginRequest;
import com.pavan.hireflow.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/candidate")
    public ResponseEntity<AuthResponse>registerCandidate(@Valid @RequestBody CandidateRegisterRequest request){

        AuthResponse response = authService.registerCandidate(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }






    // Testing API's


    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Authenticated successfully");
    }


    @GetMapping("/candidate-test")
    public ResponseEntity<String> candidateTest(){
        return ResponseEntity.ok("Candidate access granted");
    }

    @GetMapping("/recruiter-test")
    public ResponseEntity<String> recruiterTest(){
        return ResponseEntity.ok("Recruiter Access granted");
    }
}
