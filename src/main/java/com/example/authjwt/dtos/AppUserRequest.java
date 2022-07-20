package com.example.authjwt.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AppUserRequest {

  @NotEmpty
  private String name;
  @Email
  @NotEmpty
  private String email;
  @Size(min = 5, message = "A senha precisa ter no minimo 5 caracteres")
  @NotEmpty
  private String password;

  public AppUserRequest(@NotEmpty String name, @Email @NotEmpty String email,
      @Size(min = 5, message = "A senha precisa ter no minimo 5 caracteres") @NotEmpty String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

}
