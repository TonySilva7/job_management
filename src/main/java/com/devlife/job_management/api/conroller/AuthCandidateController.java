package com.devlife.job_management.api.conroller;

import com.devlife.job_management.api.model.AuthCandidateDTO;
import com.devlife.job_management.api.model.AuthCandidateResDTO;
import com.devlife.job_management.domain.service.auth.AuthCandidateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthCandidateController {

    private AuthCandidateService authCandidateService;

    @PostMapping("/candidate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthCandidateDTO authCandidateDTO) {
        try {
            var token = authCandidateService.authenticate(authCandidateDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
