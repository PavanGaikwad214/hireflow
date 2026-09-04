package com.pavan.hireflow.job.entity;

import com.pavan.hireflow.candidate.entity.Skill;
import com.pavan.hireflow.common.enums.JobStatus;
import com.pavan.hireflow.company.entity.Company;
import com.pavan.hireflow.company.entity.RecruiterProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String location;

    private String employmentType;

    private Double maxSalary;

    private Double minSalary;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private RecruiterProfile recruiter;

    @ManyToMany
    @JoinTable(
            name = "job_skills",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill>  requiredSkills = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private JobStatus  status;
}
