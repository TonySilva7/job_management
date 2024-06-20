package com.devlife.job_management.modules.company.entities;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.devlife.job_management.modules.jobs.entities.Job;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "tb_company")
@Data
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull(message = "O campo [name] não pode ser vazio")
  @Column(nullable = false)
  @Pattern(regexp = "\\S+", message = "O campo [username] não pode ter espaços")
  private String username;

  @Email(message = "O campo [email] deve conter um email válido")
  private String email;

  @Length(min = 10, max = 100, message = "O campo [password] deve conter entre 10 e 100 caracteres")
  private String password;

  private String website;

  private String name;

  private String description;
  
  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  // @JsonManagedReference
  private Set<Job> jobs = new HashSet<>();

  @CreationTimestamp
  private OffsetDateTime createdAt;
}
