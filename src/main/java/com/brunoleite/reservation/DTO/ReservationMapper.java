package com.brunoleite.reservation.DTO;

import java.util.List;

import com.brunoleite.reservation.model.Reservation;

public class ReservationMapper {
	
	public static ReservationResponseDTO toDTO(Reservation reservation) {
		return new ReservationResponseDTO(
			reservation.getTable().getName(),
			reservation.getUser().getUsername(),
			reservation.getTable().getCapacity(),
			reservation.getReservationDate().toString(),
			reservation.getId().toString()
		);
	}
	
	public static List<ReservationResponseDTO> toDTOList(List<Reservation> reservations){
		return reservations.stream()
				.map(ReservationMapper::toDTO)
				.toList();
	}
	
}
