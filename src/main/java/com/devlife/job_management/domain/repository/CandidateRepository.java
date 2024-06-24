package com.devlife.job_management.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devlife.job_management.domain.model.Candidate;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID>{
  Optional<Candidate> findByUsernameOrEmail(String username, String email);
}
