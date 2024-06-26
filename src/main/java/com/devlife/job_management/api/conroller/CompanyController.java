package com.devlife.job_management.api.conroller;

import com.devlife.job_management.domain.model.Company;
import com.devlife.job_management.domain.service.CompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Object> createCompany(@Valid @RequestBody Company companyEntity) {

        Company company = companyService.create(companyEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(company);

    }

    @GetMapping
    public ResponseEntity<List<Company>> getMethodName() {
        List<Company> companies = companyService.getAllCompanies();

        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable String id) {
        var optional = companyService.getCompanyById(id);

        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
