package com.example.authjwt.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AppUserResponse {

  private long id;
  private String name;
  private String email;

  public AppUserResponse(long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

}
