package com.example.authjwt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.authjwt.dtos.AppUserRequest;
import com.example.authjwt.dtos.AppUserResponse;
import com.example.authjwt.entities.AppUser;
import com.example.authjwt.repositories.AppUserRepository;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceImplTest {

  @Mock
  private AppUserRepository appUserRepository;
  @Mock
  private BCryptPasswordEncoder encoder;

  @InjectMocks
  private AppUserServiceImpl appUserServiceImpl;

  @Nested
  class SaveUser {

    @BeforeEach
    void setUp() {
      AppUser user = createAppUser();
      when(appUserRepository.save(any(AppUser.class))).thenReturn(user);
    }

    @Test
    void createUser_ShouldReturnsUserResponse_WhenSuccessful() {
      AppUserRequest userRequest = new AppUserRequest("Andre", "andre@hotmail.com",
          "12345");
      AppUserResponse userSaved = appUserServiceImpl.createUser(userRequest);

      assertThat(userSaved).isNotNull();
      assertThat(userSaved.getId()).isEqualTo(1);
      assertThat(userSaved.getName()).isEqualTo(userRequest.getName());
      assertThat(userSaved.getEmail()).isEqualTo(userRequest.getEmail());
    }
  }

  @Nested
  class GetUsers {

    @BeforeEach
    void setUp() {
      AppUser user = createAppUser();
      when(appUserRepository.findAll()).thenReturn(List.of(user));
    }

    @Test
    void listUsers_ShouldReturnsListOfUsersResponse_WhenSucessful() {
      List<AppUserResponse> listUsers = appUserServiceImpl.listUsers();
      AppUserResponse user = appUserServiceImpl.listUsers().get(0);

      assertThat(listUsers).isNotEmpty().isNotNull().hasSize(1);
      assertThat(listUsers.get(0)).isEqualTo(user);
    }
  }

  @Nested
  class SpringSecurityMethod {

    @Test
    void loadByUsername_ShouldReturnsUserDetail_WhenSuccessful() {
      AppUser user = createAppUser();
      when(appUserRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
      UserDetails userDetails = appUserServiceImpl.loadUserByUsername("andre@hotmail.com");

      assertThat(userDetails).isNotNull().isInstanceOf(UserDetails.class);
      assertThat(userDetails.getUsername()).isEqualTo("andre@hotmail.com");
    }

    @Test
    void loadByUsername_ShouldReturnsThrowException_WhenUserDoesntExist() {
      when(appUserRepository.findByEmail(anyString())).thenThrow(UsernameNotFoundException.class);

      assertThrows(UsernameNotFoundException.class,
          () -> appUserServiceImpl.loadUserByUsername("andre-silva@hotmail.com"));
    }
  }

  public AppUser createAppUser() {
    return new AppUser(1, "Andre", "andre@hotmail.com",
        "12345");
  }
}
