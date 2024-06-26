package com.devlife.job_management.api.conroller;

import java.util.List;

import com.devlife.job_management.api.model.ProfileCandidateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devlife.job_management.domain.model.Candidate;
import com.devlife.job_management.domain.service.CandidateService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/candidates")
@AllArgsConstructor
public class CandidateController {

    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Candidate candidateEntity) {

        Candidate candidate = candidateService.create(candidateEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getCandidates() {

        List<Candidate> candidates = this.candidateService.getCandidates();

        return ResponseEntity.status(HttpStatus.OK).body(candidates);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Object> getProfile(@PathVariable(name = "id") String id) {

        ProfileCandidateDTO profile = this.candidateService.getProfile(id);

        // print `profile`
        System.out.println("ENTROOOOOOU");

        return ResponseEntity.ok(profile);
    }

}
