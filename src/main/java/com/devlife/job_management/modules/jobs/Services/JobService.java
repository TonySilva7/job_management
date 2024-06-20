package com.devlife.job_management.modules.jobs.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devlife.job_management.modules.jobs.entities.Job;
import com.devlife.job_management.modules.jobs.repositories.JobRepository;

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
