package com.pavan.hireflow.candidate.repository;

import com.pavan.hireflow.candidate.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByCandidateProfile_Id(Long candidateProfileId);

    Optional<Experience> findByIdAndCandidateProfile_Id(Long experienceId, Long candidateProfileId);
}
