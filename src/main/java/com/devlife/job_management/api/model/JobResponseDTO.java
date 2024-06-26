package com.devlife.job_management.api.model;

import com.devlife.job_management.domain.model.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDTO {

    private String description;
    private String benefits;
    private String level;
    private String id;

    public static JobResponseDTO toDTO(Job jobEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jobEntity, JobResponseDTO.class);
    }

    public static Job toEntity(JobResponseDTO jobRequestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jobRequestDTO, Job.class);
    }
}
