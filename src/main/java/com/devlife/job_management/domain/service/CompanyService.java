package com.devlife.job_management.domain.service;

import java.util.UUID;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import com.devlife.job_management.domain.exception.UserAlreadyExistsException;
import com.devlife.job_management.domain.model.Company;
import com.devlife.job_management.domain.repository.CompanyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {

    private CompanyRepository companyRepository;
    private PasswordEncoder passwordEncoder;

    public Company create(Company companyEntity) {

        String username = companyEntity.getUsername();
        String email = companyEntity.getEmail();

        this.companyRepository.findByUsernameOrEmail(username, email).ifPresent((user) -> {
            throw new UserAlreadyExistsException("Usuário já existe!");
        });

        String password = companyEntity.getPassword();
        companyEntity.setPassword(passwordEncoder.encode(password));

        return this.companyRepository.save(companyEntity);
    }

    public List<Company> getAllCompanies() {

        return this.companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(String id) {
        UUID uuid = UUID.fromString(id);

        return companyRepository.findById(uuid);
    }
}
