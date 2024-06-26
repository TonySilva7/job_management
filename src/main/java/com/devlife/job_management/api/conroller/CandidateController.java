package com.devlife.job_management.api.conroller;

import com.devlife.job_management.api.model.ProfileCandidateDTO;
import com.devlife.job_management.domain.model.Candidate;
import com.devlife.job_management.domain.service.CandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@AllArgsConstructor
public class CandidateController {

    private CandidateService candidateService;

    @PostMapping("/signup")
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody Candidate candidateEntity) {

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

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> getProfile2(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");

        ProfileCandidateDTO profile = this.candidateService.getProfile(candidateId.toString());

        return ResponseEntity.ok(profile);
    }

}
