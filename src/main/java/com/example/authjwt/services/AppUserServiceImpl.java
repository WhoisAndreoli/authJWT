package com.example.authjwt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  private final BCryptPasswordEncoder encoder;

  @Override
  public AppUserResponse createUser(AppUserRequest appUserRequest) {

    if (appUserRepository.findByEmail(appUserRequest.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException("O email escolhido ja esta sendo usado!");
    } else {
      AppUser user = new AppUser(appUserRequest.getNome(), appUserRequest.getEmail(), appUserRequest.getPassword());
      user.setPassword(encoder.encode(user.getPassword()));
      user = appUserRepository.save(user);
      return new AppUserResponse(user.getId(), user.getName(), user.getEmail());
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = appUserRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

    return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
  }

  @Override
  public List<AppUserResponse> listUsers() {
    return appUserRepository.findAll().stream()
        .map(user -> new AppUserResponse(user.getId(), user.getName(), user.getEmail()))
        .collect(Collectors.toList());

  }

}
