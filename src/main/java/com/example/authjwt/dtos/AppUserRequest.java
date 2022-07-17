package com.example.authjwt.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AppUserRequest {

  private String nome;
  @Email
  private String email;
  @Min(value = 5)
  private String password;
}
