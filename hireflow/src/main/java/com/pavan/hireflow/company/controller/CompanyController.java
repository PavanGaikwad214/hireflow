package com.pavan.hireflow.company.controller;

import com.pavan.hireflow.company.dto.CompanyRequest;
import com.pavan.hireflow.company.dto.CompanyResponse;
import com.pavan.hireflow.company.service.CompanyService;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

//    @PostMapping("create")
//    public ResponseEntity<CompanyResponse> createCompany(
//            @RequestBody CompanyRequest request
//            ){
//
//        CompanyResponse company = companyService.createCompany(request);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(company);
//
//    }
}
