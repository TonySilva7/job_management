package com.devlife.job_management.modules.jobs.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.devlife.job_management.modules.company.entities.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "tb_job")
@Data
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String description;
  private String benefits;

  @NotBlank(message = "O campo [level] é obrigatório")
  private String level;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  // @JoinColumn(name = "company_id", insertable = false, updatable = false)
  // @JsonBackReference
  private Company company;

  // @Column(name = "company_id")
  // private UUID companyId;

  @CreationTimestamp
  private OffsetDateTime createdAt;
}
