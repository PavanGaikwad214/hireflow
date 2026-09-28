package com.pavan.hireflow.company.repository;

import com.pavan.hireflow.company.entity.RecruiterNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruiterNoteRepository extends JpaRepository<RecruiterNote, Long> {

    List<RecruiterNote> findByApplication_Id(Long applicationId);

    Optional<RecruiterNote> findByIdAndRecruiter_Id(Long noteId, Long recruiterId);
}
