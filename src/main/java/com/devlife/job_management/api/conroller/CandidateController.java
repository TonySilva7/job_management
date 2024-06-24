package com.devlife.job_management.api.conroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlife.job_management.domain.model.Candidate;
import com.devlife.job_management.domain.service.CandidateService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/candidates")
@AllArgsConstructor
public class CandidateController {

  private CandidateService candidateService;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody Candidate candidateEntity) {
//    try {
      Candidate candidate = candidateService.create(candidateEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    }
  }

  @GetMapping("/")
  public ResponseEntity<List<Candidate>> getCandidates() {

    List<Candidate> candidates = this.candidateService.getCandidates();

    return ResponseEntity.status(HttpStatus.OK).body(candidates);
  }

}
