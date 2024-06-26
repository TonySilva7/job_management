package com.devlife.job_management.api.conroller;

import com.devlife.job_management.api.model.ProfileCandidateDTO;
import com.devlife.job_management.domain.model.Candidate;
import com.devlife.job_management.domain.model.Job;
import com.devlife.job_management.domain.service.CandidateService;
import com.devlife.job_management.domain.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private JobService jobService;

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


    @GetMapping("/jobs/all")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<List<Job>> getAllJobs() {
        var jobs = this.jobService.getAllJobs();

        return ResponseEntity.ok(jobs);
    }


    @Tag(name = "Candidato", description = "Informações do candidato")
    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = Job.class)))
            })
    })
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> findJobByFilter(@RequestParam String filter) {
        var jobs = this.jobService.getJobsByFilter(filter);

        return ResponseEntity.ok(jobs);
    }

}
