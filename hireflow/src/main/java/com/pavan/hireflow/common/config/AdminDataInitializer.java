package com.pavan.hireflow.common.config;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.auth.repository.UserRepository;
import com.pavan.hireflow.common.enums.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){

        if(userRepository.existsByEmail("admin@hireflow.com")) return;

        User admin = new User();

        admin.setFirstName("HireFlow");
        admin.setLastName("Admin");
        admin.setEmail("admin@hireflow.com");
        admin.setPassword(passwordEncoder.encode("Admin@12345"));
        admin.setRole(Role.ADMIN);
        admin.setActive(true);
        admin.setEmailVerified(true);

        userRepository.save(admin);

        System.out.println("===== DEFAULT ADMIN CREATED =====");
        System.out.println("Email: admin@hireflow.com");
        System.out.println("Password: Admin@12345");
        System.out.println("=================================");

    }
}
