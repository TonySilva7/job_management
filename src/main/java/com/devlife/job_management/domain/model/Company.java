package com.devlife.job_management.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "tb_company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @EqualsAndHashCode.Include
  private UUID id;

  @NotNull(message = "O campo [username] não pode ser vazio")
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

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Job> jobs = new HashSet<>();

  @CreationTimestamp
  private OffsetDateTime createdAt;
}
