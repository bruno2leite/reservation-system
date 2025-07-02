package com.brunoleite.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunoleite.reservation.DTO.AuthenticationRequestDTO;
import com.brunoleite.reservation.DTO.LoginResponseDTO;
import com.brunoleite.reservation.DTO.RegisterRequestDTO;
import com.brunoleite.reservation.model.UserModel;
import com.brunoleite.reservation.repository.UserRepository;
import com.brunoleite.reservation.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO data) {
		System.out.println("Authentication Controller");
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((UserDetails)auth.getPrincipal()); 
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}	
	
	@PostMapping("/register")
	public ResponseEntity<RegisterRequestDTO> register(@RequestBody @Valid RegisterRequestDTO data){
		if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		UserModel user = new UserModel(data.name(), data.email(), encryptedPassword, data.role());
		
		this.userRepository.save(user);
		
		return ResponseEntity.ok().build();
	}

}
