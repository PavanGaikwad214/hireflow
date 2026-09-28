package com.pavan.hireflow.candidate.repository;

import com.pavan.hireflow.candidate.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    List<SavedJob> findByCandidateProfile_Id(Long candidateProfileId);

    Optional<SavedJob> findByCandidateProfile_IdAndJob_Id(Long candidateProfileId, Long jobId);

    Optional<SavedJob> findByIdAndCandidateProfile_Id(Long savedJobId, Long CandidateProfileId);
}
