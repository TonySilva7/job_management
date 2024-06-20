package com.devlife.job_management.modules.candidate.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devlife.job_management.modules.candidate.entities.CandidateEntity;


@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{
  Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
}
