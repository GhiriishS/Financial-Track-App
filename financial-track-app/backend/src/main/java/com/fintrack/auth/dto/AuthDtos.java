package com.fintrack.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {

  public static class RegisterRequest {
    @NotBlank
    @Size(max = 100)
    public String name;

    @NotBlank
    @Email
    @Size(max = 150)
    public String email;

    @NotBlank
    @Size(min = 8, max = 200)
    public String password;
  }

  public static class LoginRequest {
    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;
  }

  public static class AuthResponse {
    public String token;
    public String name;
    public String email;

    public AuthResponse(String token, String name, String email) {
      this.token = token;
      this.name = name;
      this.email = email;
    }
  }
}
