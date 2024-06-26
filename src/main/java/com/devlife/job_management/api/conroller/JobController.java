package com.devlife.job_management.api.conroller;

import java.util.List;

import com.devlife.job_management.api.model.JobRequestDTO;
import com.devlife.job_management.api.model.JobResponseDTO;
import com.devlife.job_management.domain.service.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlife.job_management.domain.service.JobService;
import com.devlife.job_management.domain.model.Job;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
public class JobController {

  private JobService jobService;
  private CompanyService companyService;

  @PostMapping("/")
  public ResponseEntity<Object> createJob(@Valid @RequestBody JobRequestDTO jobRequestDto, HttpServletRequest request) {
    try {
      var companyId = request.getAttribute("company_id");

      if(companyId == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company not found");
      }

      var c = companyService.getCompanyById(companyId.toString())
          .orElseThrow(() -> new EntityNotFoundException("Company not found"));

      Job job = JobRequestDTO.toEntity(jobRequestDto);

      job.setCompany(c);

      job = jobService.create(job);

      JobResponseDTO res = JobResponseDTO.toDTO(job);

      return ResponseEntity.status(HttpStatus.CREATED).body(res);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<Job>> getMethodName() {
    return ResponseEntity.status(HttpStatus.OK).body(jobService.getAllJobs());
  }

}
