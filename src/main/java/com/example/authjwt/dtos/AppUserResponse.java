package com.example.authjwt.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AppUserResponse {

  private long id;
  private String nome;
  private String email;

  public AppUserResponse(long id, String nome, String email) {
    this.id = id;
    this.nome = nome;
    this.email = email;
  }

}
