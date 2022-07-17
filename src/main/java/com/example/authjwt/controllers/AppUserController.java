package com.example.authjwt.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authjwt.dtos.AppUserRequest;
import com.example.authjwt.dtos.AppUserResponse;
import com.example.authjwt.services.AppUserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

  private final AppUserServiceImpl appUserServiceImpl;

  @PostMapping
  public ResponseEntity<AppUserResponse> createUser(@RequestBody AppUserRequest appUserRequest) {
    return new ResponseEntity<>(appUserServiceImpl.createUser(appUserRequest), HttpStatus.CREATED);
  }
}
