package com.devlife.job_management.modules.jobs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devlife.job_management.modules.jobs.entities.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
}
