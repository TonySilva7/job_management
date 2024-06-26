package com.devlife.job_management.api.model;

import com.devlife.job_management.domain.model.Candidate;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
public class ProfileCandidateDTO {

    private String description;
    private String username;
    private String email;
    private UUID id;
    private String name;

    public static ProfileCandidateDTO toDTO(Candidate profileCandidate) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(profileCandidate, ProfileCandidateDTO.class);
    }

    public static Candidate toEntity(ProfileCandidateDTO profileCandidateDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(profileCandidateDTO, Candidate.class);
    }
}
