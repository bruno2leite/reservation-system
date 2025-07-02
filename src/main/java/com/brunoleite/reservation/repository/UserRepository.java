package com.brunoleite.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.brunoleite.reservation.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, String>{
	UserDetails findByEmail(String email);
}
