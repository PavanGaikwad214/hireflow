package com.pavan.hireflow.candidate.repository;

import com.pavan.hireflow.candidate.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCandidateProfile_Id(Long candidateProfileId);

    Optional<Project> findByIdAndCandidateProfile_Id(Long projectId, Long candidateProfileId);
}
