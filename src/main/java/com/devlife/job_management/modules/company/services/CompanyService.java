package com.devlife.job_management.modules.company.services;

import org.springframework.stereotype.Service;

import java.util.List;
import com.devlife.job_management.exceptions.UserFoundException;
import com.devlife.job_management.modules.company.entities.Company;
import com.devlife.job_management.modules.company.repositories.CompanyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {

  private CompanyRepository companyRepository;

  public Company create(Company companyEntity) {

    String username = companyEntity.getUsername();
    String email = companyEntity.getEmail();

    this.companyRepository.findByUsernameOrEmail(username, email).ifPresent((user) -> {
      throw new UserFoundException("Usuário já existe!");
    });

    return this.companyRepository.save(companyEntity);
  }

  public List<Company> getAllCompanies() {
    List<Company> companies = this.companyRepository.findAll();
    
    return companies;
  }
}
