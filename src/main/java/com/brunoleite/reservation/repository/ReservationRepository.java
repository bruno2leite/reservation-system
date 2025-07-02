package com.brunoleite.reservation.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brunoleite.reservation.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, String>{
	List<Reservation> findByUserEmail(String email);
	boolean existsByReservationTime(LocalTime reservationTime);
	@Query("SELECT r FROM Reservation r WHERE r.table.id IN :tableIds AND " +
		       "(:start < r.endTime AND :end > r.reservationTime)")
	List<Reservation> findConflicts(
		@Param("tableIds") List<String> tableIds,
		@Param("start") LocalTime start,
		@Param("end") LocalTime end
	);
}
