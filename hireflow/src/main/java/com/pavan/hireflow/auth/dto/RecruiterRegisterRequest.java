package com.pavan.hireflow.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecruiterRegisterRequest {

    private String firstName;

    private String lastName;

    private String phone;

    private String password;

    private String email;


    private String companyName;
    private String designation;
}
