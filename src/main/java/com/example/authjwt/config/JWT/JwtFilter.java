package com.example.authjwt.config.JWT;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authjwt.dtos.AppUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private String secretKey = "a73b5898-834f-4b95-88b0-0cc1e0ded7fc";

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      AppUserRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), AppUserRequest.class);

      return authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
    } catch (IOException e) {
      throw new AuthenticationCredentialsNotFoundException(e.getMessage());
    }

  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    var user = (User) authResult.getPrincipal();
    String jwtToken = JWT.create().withSubject(user.getUsername()).withIssuedAt(new Date(System.currentTimeMillis()))
        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100))
        .withIssuer(request.getRequestURI()).sign(Algorithm.HMAC512(secretKey));

    response.setHeader("Authorization", "Bearer " + jwtToken);
  }

}
