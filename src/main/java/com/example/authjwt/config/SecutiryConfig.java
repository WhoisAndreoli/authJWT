package com.example.authjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.authjwt.config.JWT.JwtFilter;
import com.example.authjwt.config.JWT.JwtValidation;

@EnableWebSecurity(debug = true)
public class SecutiryConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers(HttpMethod.GET).authenticated().anyRequest().permitAll().and()
        .apply(MyCustomDsl.customDsl()).and()
        .httpBasic().and().build();
  }

  public static class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
      JwtFilter jwtFilter = new JwtFilter(authenticationManager);
      jwtFilter.setFilterProcessesUrl("/users/login");
      http.addFilter(jwtFilter);
      http.addFilterBefore(new JwtValidation(), JwtFilter.class);
    }

    public static MyCustomDsl customDsl() {
      return new MyCustomDsl();
    }
  }

  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
