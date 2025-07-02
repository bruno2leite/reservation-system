package com.brunoleite.reservation.DTO;

import java.time.LocalTime;

public record ReservationDTO(int quantity,
							 LocalTime date) {
		
}
