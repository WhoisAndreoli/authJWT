package com.example.authjwt.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.authjwt.entities.AppUser;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AppUserRepositoryTest {

  @Autowired
  private AppUserRepository appUserRepository;

  @Test
  void save_User_WhenSuccessful() {
    AppUser userToBeSaved = createAppUser();
    AppUser userSaved = appUserRepository.saveAndFlush(userToBeSaved);

    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(userToBeSaved.getName());

  }

  @Test
  void find_UserByEmail_WhenSuccessful() {
    AppUser userSaved = appUserRepository.saveAndFlush(createAppUser());
    Optional<AppUser> foundUser = appUserRepository.findByEmail(userSaved.getEmail());

    assertThat(userSaved).isEqualTo(foundUser.get());
    assertThat(userSaved.getId()).isEqualTo(foundUser.get().getId());
    assertThat(foundUser).isPresent();
  }

  @Test
  void find_AllUsers_WhenSuccessful() {
    AppUser userSaved = appUserRepository.saveAndFlush(createAppUser());
    List<AppUser> allUsers = appUserRepository.findAll();

    assertThat(allUsers).isNotEmpty().isNotNull();
    assertThat(allUsers.get(0)).isEqualTo(userSaved);
  }

  public AppUser createAppUser() {
    return new AppUser("Andre", "andre@hotmail.com",
        "12345");
  }
}
