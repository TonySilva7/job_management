package com.devlife.job_management.api.conroller;

import com.devlife.job_management.api.model.AuthCompanyDTO;
import com.devlife.job_management.api.model.AuthCompanyResDTO;
import com.devlife.job_management.domain.service.auth.AuthCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthCompanyController {
    private AuthCompanyService authCompanyService;

    @PostMapping("/company")
    public ResponseEntity<Object> handleAuthCompany(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        String token = authCompanyService.authenticate(authCompanyDTO);
        AuthCompanyResDTO resToken = new AuthCompanyResDTO(token);

        return ResponseEntity.ok(resToken);


    }
};
