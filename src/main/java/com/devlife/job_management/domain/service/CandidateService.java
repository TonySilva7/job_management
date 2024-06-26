package com.devlife.job_management.domain.service;

import java.util.List;
import java.util.UUID;

import com.devlife.job_management.api.model.ProfileCandidateDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Candidate create(Candidate candidateEntity) {
        String username = candidateEntity.getUsername();
        String email = candidateEntity.getEmail();

        this.candidateRepository.findByUsernameOrEmail(username, email).ifPresent((user) -> {
            throw new UserAlreadyExistsException("Usuário já existe!");
        });

        String password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }

    public List<Candidate> getCandidates() {

        return this.candidateRepository.findAll();
    }

    public ProfileCandidateDTO getProfile(String id) {
        UUID uuid = UUID.fromString(id);

        var candidate = candidateRepository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException("Candidato não encontrado"));

        return ProfileCandidateDTO.toDTO(candidate);
    }
}
