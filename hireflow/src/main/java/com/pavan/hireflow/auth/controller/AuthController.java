package com.pavan.hireflow.auth.controller;

import com.pavan.hireflow.auth.dto.CandidateRegisterRequest;
import com.pavan.hireflow.auth.service.AuthService;
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
    public ResponseEntity<String>registerCandidate(@RequestBody CandidateRegisterRequest request){

        return null;
    }
}
