package com.devlife.job_management.modules.company.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlife.job_management.modules.company.entities.Company;
import com.devlife.job_management.modules.company.services.CompanyService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {

  private CompanyService companyService;

  @PostMapping("/")
  public ResponseEntity<Object> createCompany(@Valid @RequestBody Company companyEntity) {
    try {
      Company company = companyService.create(companyEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(company);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<Company>> getMethodName() {
    List<Company> companies = companyService.getAllCompanies();

    return ResponseEntity.status(HttpStatus.OK).body(companies);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompany(@PathVariable String id) {
    Optional<Company> optional = companyService.getCompanyById(id);

    if (optional.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(optional.get());
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

}
