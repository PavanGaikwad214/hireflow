package com.pavan.hireflow.company.entity;

import com.pavan.hireflow.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recruiter_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn( name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn( name = "company_id", nullable = false)
    private Company company;

    private String designation;
}
