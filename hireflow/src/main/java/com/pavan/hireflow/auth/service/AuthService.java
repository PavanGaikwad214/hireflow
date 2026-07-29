package com.pavan.hireflow.auth.service;

import com.pavan.hireflow.auth.dto.AuthResponse;
import com.pavan.hireflow.auth.dto.CandidateRegisterRequest;
import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.auth.repository.UserRepository;
import com.pavan.hireflow.common.enums.Role;
import com.pavan.hireflow.common.exception.EmailAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public AuthResponse registerCandidate(CandidateRegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email is Already Registered");
        }


            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setPassword(request.getPassword());
            user.setRole(Role.CANDIDATE);
            user.setActive(true);
            user.setEmailVerified(false);

            userRepository.save(user);

            AuthResponse response = new AuthResponse();
            response.setMessage("Candidate registered Successfully");


        return response;
    }

}
