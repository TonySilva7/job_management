package com.devlife.job_management.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devlife.job_management.domain.exception.UserAlreadyExistsException;
import com.devlife.job_management.domain.model.Candidate;
import com.devlife.job_management.domain.repository.CandidateRepository;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CandidateService {

  private CandidateRepository candidateRepository;

  @Transactional
  public Candidate create(Candidate candidateEntity) {
    String username = candidateEntity.getUsername();
    String email = candidateEntity.getEmail();

    this.candidateRepository.findByUsernameOrEmail(username, email).ifPresent((user) -> {
      throw new UserAlreadyExistsException("Usuário já existe!");
    });

    return this.candidateRepository.save(candidateEntity);
  }

  public List<Candidate> getCandidates() {

      return this.candidateRepository.findAll();
  }
}
