package com.devlife.job_management.modules.candidate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlife.job_management.modules.candidate.entities.CandidateEntity;
import com.devlife.job_management.modules.candidate.services.CandidateService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/candidate")
@AllArgsConstructor
public class CandidateController {

  private CandidateService candidateService;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
			CandidateEntity candidate = candidateService.create(candidateEntity);

			return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
  }

}
