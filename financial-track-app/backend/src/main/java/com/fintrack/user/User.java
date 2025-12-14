package com.fintrack.user;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "app_user", indexes = {
    @Index(name = "idx_user_email", columnList = "email", unique = true)
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 150)
  private String email;

  @Column(nullable = false, length = 200)
  private String passwordHash;

  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  public Long getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }
  public String getPasswordHash() { return passwordHash; }
  public Instant getCreatedAt() { return createdAt; }

  public void setId(Long id) { this.id = id; }
  public void setName(String name) { this.name = name; }
  public void setEmail(String email) { this.email = email; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
