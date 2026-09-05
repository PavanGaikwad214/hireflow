package com.pavan.hireflow.candidate.repository;

import com.pavan.hireflow.candidate.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification,Long> {

    List<Certification> findByCandidateProfile_Id(Long candidateProfileId);

    Optional<Certification> findByIdAndCandidateProfile_Id(Long certificationId, Long candidateProfileId);
}
