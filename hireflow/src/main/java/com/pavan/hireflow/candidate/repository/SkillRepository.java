package com.pavan.hireflow.candidate.repository;

import com.pavan.hireflow.candidate.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {

    Optional<Skill> findByNameIgnoreCase(String name);
}
