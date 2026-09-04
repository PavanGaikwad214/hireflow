package com.pavan.hireflow.job.repository;

import com.pavan.hireflow.common.enums.JobStatus;
import com.pavan.hireflow.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    List<Job> findByStatus(JobStatus status);
}
