package com.example.authjwt.services;

import com.example.authjwt.dtos.AppUserRequest;
import com.example.authjwt.dtos.AppUserResponse;

public interface AppUserService {

  AppUserResponse createUser(AppUserRequest appUserRequest);
}
