package com.example.authjwt.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.authjwt.dtos.AppUserRequest;
import com.example.authjwt.dtos.AppUserResponse;
import com.example.authjwt.entities.AppUser;
import com.example.authjwt.exceptions.EmailAlreadyUsedException;
import com.example.authjwt.repositories.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {

  private final AppUserRepository appUserRepository;

  @Override
  public AppUserResponse createUser(AppUserRequest appUserRequest) {

    if (appUserRepository.findByEmail(appUserRequest.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException("O email escolhido ja esta sendo usado!");
    } else {
      AppUser user = new AppUser(appUserRequest.getNome(), appUserRequest.getEmail(), appUserRequest.getPassword());
      user = appUserRepository.save(user);
      return new AppUserResponse(user.getId(), user.getName(), user.getEmail());
    }
  }

}
