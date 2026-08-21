package com.pavan.hireflow.auth.service;

import com.pavan.hireflow.auth.dto.AdminCreateRecruiterRequest;
import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.auth.repository.UserRepository;
import com.pavan.hireflow.common.enums.Role;
import com.pavan.hireflow.common.exception.CompanyNotFoundException;
import com.pavan.hireflow.common.exception.EmailAlreadyExistsException;
import com.pavan.hireflow.company.dto.CompanyRequest;
import com.pavan.hireflow.company.dto.CompanyResponse;
import com.pavan.hireflow.company.entity.Company;
import com.pavan.hireflow.company.entity.RecruiterProfile;
import com.pavan.hireflow.company.repository.CompanyRepository;
import com.pavan.hireflow.company.repository.RecruiterProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository,
                        CompanyRepository companyRepository,
                        RecruiterProfileRepository recruiterProfileRepository,
                        PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void createRecruiter(AdminCreateRecruiterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found"));

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.RECRUITER);

        user.setActive(true);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

        RecruiterProfile recruiterProfile = new RecruiterProfile();

        recruiterProfile.setUser(savedUser);
        recruiterProfile.setCompany(company);
        recruiterProfile.setDesignation(request.getDesignation());

        recruiterProfileRepository.save(recruiterProfile);
    }
}
