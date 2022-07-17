package com.example.authjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authjwt.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
