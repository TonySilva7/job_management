package com.devlife.job_management.modules.jobs.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlife.job_management.modules.jobs.Services.JobService;
import com.devlife.job_management.modules.jobs.entities.Job;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
public class JobController {

  private JobService companyService;

  @PostMapping("/")
  public ResponseEntity<Object> createJob(@Valid @RequestBody Job companyEntity) {
    try {
      Job company = companyService.create(companyEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(company);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<Job>> getMethodName() {
    return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllJobs());
  }

}
