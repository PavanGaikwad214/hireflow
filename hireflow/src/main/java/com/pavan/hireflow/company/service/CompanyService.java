package com.pavan.hireflow.company.service;

import com.pavan.hireflow.common.exception.CompanyNotFoundException;
import com.pavan.hireflow.company.dto.CompanyRequest;
import com.pavan.hireflow.company.dto.CompanyResponse;
import com.pavan.hireflow.company.entity.Company;
import com.pavan.hireflow.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    private CompanyResponse mapToResponse(Company company){

        return new  CompanyResponse(
                company.getId(),
                company.getName(),
                company.getDescription(),
                company.getWebsite(),
                company.getLogoUrl(),
                company.getLocation()
        );
    }

    public CompanyResponse createCompany(CompanyRequest request){

        Company company = new Company();

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLogoUrl(request.getLogoUrl());
        company.setLocation(request.getLocation());

        Company savedCompany = companyRepository.save(company);

        return mapToResponse(savedCompany);
    }

    
    public CompanyResponse getCompany(Long companyId){

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found"));

        return mapToResponse(company);
    }
}
