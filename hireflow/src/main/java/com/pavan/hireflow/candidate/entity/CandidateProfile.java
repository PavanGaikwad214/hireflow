package com.pavan.hireflow.candidate.entity;

import com.pavan.hireflow.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "candidate_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String summary;

    private String headline;

    private String resumeUrl;

    private String githubUrl;

    private String linkedinUrl;

    private String portfolioUrl;
}
