package com.pavan.hireflow.auth.controller;

import com.pavan.hireflow.auth.dto.AdminCreateRecruiterRequest;
import com.pavan.hireflow.auth.service.AdminService;
import com.pavan.hireflow.company.dto.CompanyRequest;
import com.pavan.hireflow.company.dto.CompanyResponse;
import com.pavan.hireflow.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;
    private final CompanyService companyService;

    public AdminController(AdminService adminService, CompanyService companyService) {
        this.adminService = adminService;
        this.companyService = companyService;
    }


    @PostMapping("/recruiter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createRecruiter(
            @Valid @RequestBody AdminCreateRecruiterRequest request
            ){

        adminService.createRecruiter(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/companies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyRequest request
            ){
        CompanyResponse response = companyService.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
