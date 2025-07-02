package com.brunoleite.reservation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunoleite.reservation.DTO.ReservationDTO;
import com.brunoleite.reservation.DTO.ReservationResponseDTO;
import com.brunoleite.reservation.service.ReservationService;

@RestController
@RequestMapping("/reserve")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	public ReservationController(ReservationService reservationService){
		this.reservationService = reservationService;
	}
	
	@GetMapping
	public ResponseEntity<List<ReservationResponseDTO>> getReservations(){
		List<ReservationResponseDTO> reservationList = reservationService.getReservations();
		return ResponseEntity.ok(reservationList);
	}
	
	@PostMapping
	public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationDTO reservationDTO){
		ReservationResponseDTO reservation = reservationService.createReservation(reservationDTO);
		return ResponseEntity.ok(reservation);
	}
	
	@PatchMapping("/remove/{id}")
	public ResponseEntity<Void> removeReservation(@PathVariable String id){
		reservationService.cancelReservation(id);
		return ResponseEntity.noContent().build();
	}
	
}
