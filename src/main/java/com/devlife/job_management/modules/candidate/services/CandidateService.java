package com.devlife.job_management.modules.candidate.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devlife.job_management.exceptions.UserFoundException;
import com.devlife.job_management.modules.candidate.entities.CandidateEntity;
import com.devlife.job_management.modules.candidate.repositories.CandidateRepository;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CandidateService {

  private CandidateRepository candidateRepository;

  @Transactional
  public CandidateEntity create(CandidateEntity candidateEntity) {
    String username = candidateEntity.getUsername();
    String email = candidateEntity.getEmail();

    this.candidateRepository.findByUsernameOrEmail(username, email).ifPresent((user) -> {
      throw new UserFoundException("Usuário já existe!");
    });

    return this.candidateRepository.save(candidateEntity);
  }

  public List<CandidateEntity> getCandidates() {

      return this.candidateRepository.findAll();
  }
}
