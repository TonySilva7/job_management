package com.devlife.job_management.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devlife.job_management.domain.model.Job;
import com.devlife.job_management.domain.repository.JobRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobService {

  private JobRepository jobRepository;

  public Job create(Job jobEntity) {

    return this.jobRepository.save(jobEntity);
  }

  public List<Job> getAllJobs() {
    return this.jobRepository.findAll();
  }
}
