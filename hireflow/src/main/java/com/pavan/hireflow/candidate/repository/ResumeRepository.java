package com.pavan.hireflow.candidate.repository;

import com.pavan.hireflow.candidate.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByCandidateProfile_Id(Long candidateProfileId);

    Optional<Resume> findByIdAndCandidateProfile_Id(Long resumeId, Long candidateProfileId);

    Optional<Resume> findByCandidateProfile_IdAndPrimaryResumeTrue(Long candidateProfileId);
}
