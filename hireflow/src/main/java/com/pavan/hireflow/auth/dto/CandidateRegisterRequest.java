package com.pavan.hireflow.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CandidateRegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;
    
    private String phone;


}
