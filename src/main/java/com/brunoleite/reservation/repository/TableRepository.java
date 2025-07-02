package com.brunoleite.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunoleite.reservation.model.TableModel;

public interface TableRepository extends JpaRepository<TableModel, String>{

}
