package com.example.authjwt.config.JWT;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtValidation extends OncePerRequestFilter {

  private String secretKey = "a73b5898-834f-4b95-88b0-0cc1e0ded7fc";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = request.getHeader("Authorization");
    if (token == null || token.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    if (!token.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    token = token.split(" ")[1];

    try {
      JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).build();
      DecodedJWT decoded = verifier.verify(token);
      String username = decoded.getSubject();
      var authToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
      authToken.setDetails(new WebAuthenticationDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
      filterChain.doFilter(request, response);
    } catch (JWTVerificationException e) {
      throw new JWTVerificationException(e.getMessage());
    }

  }

}
