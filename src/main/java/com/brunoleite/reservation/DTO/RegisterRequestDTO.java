package com.brunoleite.reservation.DTO;

import com.brunoleite.reservation.model.UserRole;


public record RegisterRequestDTO(String name, 
				String email, 
				String password,
				UserRole role) {

}
