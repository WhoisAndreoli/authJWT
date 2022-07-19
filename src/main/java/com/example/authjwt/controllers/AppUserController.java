package com.example.authjwt.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authjwt.dtos.AppUserRequest;
import com.example.authjwt.dtos.AppUserResponse;
import com.example.authjwt.dtos.LoginRequest;
import com.example.authjwt.services.AppUserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserController {

  private final AppUserServiceImpl appUserServiceImpl;

  @PostMapping
  public ResponseEntity<AppUserResponse> createUser(@RequestBody @Valid AppUserRequest appUserRequest) {
    return new ResponseEntity<>(appUserServiceImpl.createUser(appUserRequest), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<AppUserResponse>> listUsers() {
    return new ResponseEntity<>(appUserServiceImpl.listUsers(), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
    return new ResponseEntity<>("Login bem sucedido", HttpStatus.ACCEPTED);
  }
}
