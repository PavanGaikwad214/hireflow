package com.pavan.hireflow.application.repository;

import com.pavan.hireflow.application.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    Optional<Interview> findByApplication_Id(Long applicationId);

    Optional<Interview> findByIdAndApplication_Job_Recruiter_Id(Long interviewId, Long recruiterId);
}
