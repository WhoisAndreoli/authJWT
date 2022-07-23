package com.example.authjwt.controllers;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.authjwt.dtos.AppUserRequest;
import com.example.authjwt.dtos.AppUserResponse;
import com.example.authjwt.services.AppUserServiceImpl;

@WebMvcTest(AppUserController.class)
@WithMockUser(username = "andre@hotmail.com", password = "12345")
public class AppUserControllerTest {

  @MockBean
  private AppUserServiceImpl appUserServiceImpl;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void createUser_ShouldReturnsAppUserResponse_WhenSuccessful() throws Exception {
    AppUserRequest userRequest = createAppUserRequest();
    when(appUserServiceImpl.createUser(userRequest)).thenReturn(createAppUserResponse());

    mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
        .content("""
            {
              "name": "Andre",
              "email": "andre@hotmail.com",
              "password": "12345"
            }
                """)
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userRequest.getEmail()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userRequest.getName()));
  }

  @Test
  void listUsers_ShouldReturnsListOfUsersResponse_WhenSucessful() throws Exception {
    when(appUserServiceImpl.listUsers()).thenReturn(List.of(createAppUserResponse()));

    mockMvc.perform(MockMvcRequestBuilders.get("/users"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Andre"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value("andre@hotmail.com"));
  }

  public AppUserRequest createAppUserRequest() {
    return new AppUserRequest("Andre", "andre@hotmail.com", "12345");
  }

  public AppUserResponse createAppUserResponse() {
    return new AppUserResponse(1, "Andre", "andre@hotmail.com");
  }

}
