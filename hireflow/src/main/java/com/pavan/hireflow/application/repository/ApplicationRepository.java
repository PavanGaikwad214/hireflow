package com.pavan.hireflow.application.repository;

import com.pavan.hireflow.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByCandidate_IdAndJob_Id(Long candidateId, Long jobId);

    List<Application> findByCandidate_Id(Long candidateId);

    List<Application> findByJob_Recruiter_Id(Long recruiterId);
}
