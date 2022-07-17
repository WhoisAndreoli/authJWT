package com.example.authjwt.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AppUserResponse {

  private long id;
  private String nome;
  private String email;
}
