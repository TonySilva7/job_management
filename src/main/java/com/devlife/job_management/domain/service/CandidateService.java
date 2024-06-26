package com.devlife.job_management.domain.service;

import com.devlife.job_management.api.model.ProfileCandidateDTO;
import com.devlife.job_management.domain.exception.ResourceNotFoundException;
import com.devlife.job_management.domain.exception.UserAlreadyExistsException;
import com.devlife.job_management.domain.model.Candidate;
import com.devlife.job_management.domain.repository.CandidateRepository;
import com.devlife.job_management.domain.service.providers.MapperProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

        var candidate = candidateRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        return MapperProvider.fromTo(candidate, ProfileCandidateDTO.class);
    }
}
