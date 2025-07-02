package com.brunoleite.reservation.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.brunoleite.reservation.DTO.ReservationDTO;
import com.brunoleite.reservation.DTO.ReservationMapper;
import com.brunoleite.reservation.DTO.ReservationResponseDTO;
import com.brunoleite.reservation.model.Reservation;
import com.brunoleite.reservation.model.TableModel;
import com.brunoleite.reservation.model.UserModel;
import com.brunoleite.reservation.repository.ReservationRepository;
import com.brunoleite.reservation.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TableService tableService;

    public ReservationService(ReservationRepository reservationRepository,
                               UserRepository userRepository,
                               TableService tableService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.tableService = tableService;
    }

    public List<ReservationResponseDTO> getReservations() {
        String email = getCurrentUserEmail();
        List<Reservation> reservations = reservationRepository.findByUserEmail(email);
        return ReservationMapper.toDTOList(reservations);
    }

    public ReservationResponseDTO createReservation(ReservationDTO reservationDTO) {
        int quantity = reservationDTO.quantity();
        if (quantity <= 0) throw new IllegalArgumentException("Invalid quantity.");

        LocalTime startTime = reservationDTO.date();
        if (startTime.isBefore(RestaurantSchedule.OPENING_TIME) || startTime.isAfter(RestaurantSchedule.CLOSING_TIME)) {
            throw new IllegalArgumentException("The restaurant is open from 6pm to 11pm.");
        }

        String email = getCurrentUserEmail();
        UserModel user = (UserModel) userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        List<TableModel> allTables = tableService.getAllTables();
        List<TableModel> availableTables = findAvailableTables(allTables, startTime);
        TableModel table = findBestAvailableTable(quantity, availableTables);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTable(table);
        reservation.setStatus(false);
        reservation.setReservationTime(startTime);
        reservation.setEndTime(startTime.plusHours(1));
        reservation.setQuantity(quantity);

        reservationRepository.save(reservation);
        return ReservationMapper.toDTO(reservation);
    }

    public void cancelReservation(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));

        String currentEmail = getCurrentUserEmail();
        if (!reservation.getUser().getEmail().equals(currentEmail)) {
            throw new IllegalArgumentException("You are not allowed to cancel this reservation.");
        }

        reservationRepository.deleteById(id);
    }

    private TableModel findBestAvailableTable(int quantity, List<TableModel> tables) {
        TableModel bestTable = null;
        int lowestDifference = Integer.MAX_VALUE;

        for (TableModel table : tables) {
            if (quantity <= table.getCapacity()) {
                int difference = table.getCapacity() - quantity;
                if (difference < lowestDifference) {
                    lowestDifference = difference;
                    bestTable = table;
                }
            }
        }

        if (bestTable == null) {
            throw new IllegalStateException("No available table found for quantity " + quantity);
        }

        return bestTable;
    }

    private List<TableModel> findAvailableTables(List<TableModel> allTables, LocalTime requestedTime) {
        LocalTime endTime = requestedTime.plusHours(1);

        List<String> tableIds = allTables.stream()
                .map(TableModel::getId)
                .collect(Collectors.toList());

        if (tableIds.isEmpty()) {
            return allTables;
        }

        List<Reservation> conflicts = reservationRepository.findConflicts(tableIds, requestedTime, endTime);

        Set<String> reservedTableIds = conflicts.stream()
                .map(r -> r.getTable().getId())
                .collect(Collectors.toSet());

        return allTables.stream()
                .filter(table -> !reservedTableIds.contains(table.getId()))
                .collect(Collectors.toList());
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static class RestaurantSchedule {
        public static final LocalTime OPENING_TIME = LocalTime.of(18, 0);
        public static final LocalTime CLOSING_TIME = LocalTime.of(23, 0);
    }
}
