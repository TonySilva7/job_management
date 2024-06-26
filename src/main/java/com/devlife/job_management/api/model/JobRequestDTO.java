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
public class JobRequestDTO {

    private String description;
    private String benefits;
    private String level;

    public static JobRequestDTO toDTO(Job jobEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jobEntity, JobRequestDTO.class);
    }

    public static Job toEntity(JobRequestDTO jobRequestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jobRequestDTO, Job.class);
    }
}
