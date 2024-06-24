package com.devlife.job_management.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @EqualsAndHashCode.Include
  private UUID id;
  private String description;
  private String benefits;

  @NotBlank(message = "O campo [level] é obrigatório")
  private String level;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  @JsonBackReference
  private Company company;

  @CreationTimestamp
  private OffsetDateTime createdAt;
}
