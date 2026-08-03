package com.pavan.hireflow.auth.service;

import com.pavan.hireflow.auth.dto.AuthResponse;
import com.pavan.hireflow.auth.dto.CandidateRegisterRequest;
import com.pavan.hireflow.auth.dto.LoginRequest;
import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.auth.repository.UserRepository;
import com.pavan.hireflow.common.enums.Role;
import com.pavan.hireflow.common.exception.AccountInactiveException;
import com.pavan.hireflow.common.exception.EmailAlreadyExistsException;
import com.pavan.hireflow.common.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse registerCandidate(CandidateRegisterRequest request) {

        //System.out.println("REGISTER REQUEST: " + request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is Already Registered");
        }


        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CANDIDATE);
        user.setActive(true);
        user.setEmailVerified(false);

        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setMessage("Candidate registered Successfully");


        return response;
    }

    public AuthResponse login(LoginRequest request){

        User user  = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(
                            "Invalid Email or Password"
                ));

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )){
            throw new InvalidCredentialsException(
                    "Invalid Email or Password"
            );
        }

        if(!user.isActive()){
            throw new AccountInactiveException(
                    "Account is inactive"
            );
        }

        AuthResponse response = new AuthResponse();
        response.setUserId(user.getId());
        response.setMessage("Login Successfully");

        return response;
    }
}

