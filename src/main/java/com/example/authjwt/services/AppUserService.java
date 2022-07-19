package com.example.authjwt.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.authjwt.dtos.AppUserRequest;
import com.example.authjwt.dtos.AppUserResponse;

public interface AppUserService extends UserDetailsService {

  UserDetails loadUserByUsername(String username);

  AppUserResponse createUser(AppUserRequest appUserRequest);

  List<AppUserResponse> listUsers();
}
