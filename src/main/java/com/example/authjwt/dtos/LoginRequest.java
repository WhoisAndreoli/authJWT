package com.example.authjwt.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequest {
  @NotEmpty
  @Email
  private String email;
  @NotEmpty
  private String password;
}
