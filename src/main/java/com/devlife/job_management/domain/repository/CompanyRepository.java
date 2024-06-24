package com.devlife.job_management.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devlife.job_management.domain.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>{
  Optional<Company> findByUsernameOrEmail(String username, String email);
}