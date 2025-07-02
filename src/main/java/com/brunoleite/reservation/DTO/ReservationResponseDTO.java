package com.brunoleite.reservation.DTO;

public record ReservationResponseDTO(String nameTable,
									 String nameUser,
									 int tableCapacity,
									 String reservationTime,
									 String reservationId) {

}
