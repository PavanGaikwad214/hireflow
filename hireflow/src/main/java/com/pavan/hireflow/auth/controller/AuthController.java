package com.pavan.hireflow.auth.controller;

import com.pavan.hireflow.auth.dto.AuthResponse;
import com.pavan.hireflow.auth.dto.CandidateRegisterRequest;
import com.pavan.hireflow.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
