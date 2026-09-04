package com.pavan.hireflow.candidate.repository;

import com.pavan.hireflow.candidate.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByCandidateProfile_Id(Long candidateProfileId);

    Optional<Education> findByIdAndCandidateProfile_Id(Long educationId, Long candidateProfileId);
}
