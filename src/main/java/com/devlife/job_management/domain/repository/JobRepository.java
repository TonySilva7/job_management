package com.devlife.job_management.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devlife.job_management.domain.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findByDescriptionContainingIgnoreCase(String title);
}
