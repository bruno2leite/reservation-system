package com.brunoleite.reservation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brunoleite.reservation.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		System.out.println("Authorization Service");
		var user = userRepository.findByEmail(email);
		 
		 if(user == null) throw new UsernameNotFoundException("User Not Found");
		 return user;
	}
}
