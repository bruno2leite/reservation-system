package com.brunoleite.reservation.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.brunoleite.reservation.DTO.TableDTO;
import com.brunoleite.reservation.model.TableModel;
import com.brunoleite.reservation.repository.TableRepository;


@Service
public class TableService {
	
	private final TableRepository tableRepository;
	
	public TableService(TableRepository tableRepository) {
		this.tableRepository = tableRepository;
	}
	
	public List<TableModel> getAllTables(){
		return tableRepository.findAll();
	}
	
	public void deleteTable(String id) {
		TableModel tableModel = tableRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not Found with id: " + id));
		tableRepository.delete(tableModel);
	}
	
	public TableModel createTable(TableDTO tableDTO){
		TableModel saveTable = new TableModel();
		saveTable.setName(tableDTO.getName());
		saveTable.setCapacity(tableDTO.getCapacity());
		saveTable.setAvailable(tableDTO.isAvailable());
		
		return tableRepository.save(saveTable);
	}
	
	public TableModel updateTable(String id, TableDTO tableDTO) {
		TableModel update = tableRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Table not found with ID: " + id));
		if(tableDTO.getName() != null) update.setName(tableDTO.getName());
		if(tableDTO.getCapacity() != 0) update.setCapacity(tableDTO.getCapacity());
		update.setAvailable(tableDTO.isAvailable());
		
		return tableRepository.save(update);
	}
	
}
