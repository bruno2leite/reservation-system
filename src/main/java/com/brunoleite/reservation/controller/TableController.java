package com.brunoleite.reservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunoleite.reservation.DTO.TableDTO;
import com.brunoleite.reservation.model.TableModel;
import com.brunoleite.reservation.service.TableService;

@RestController
@RequestMapping("/table")
public class TableController {
	private final TableService tableService;
	
	public TableController(TableService tableService) {
		this.tableService = tableService;
	}
	
	@GetMapping
	public ResponseEntity<List<TableModel>> getAllTables(){
		List<TableModel> tableList = tableService.getAllTables();					
		return ResponseEntity.ok(tableList);
	}
	
	@PostMapping
	public ResponseEntity<TableModel> createTable(@RequestBody TableDTO tableDTO){
		TableModel saveTable = tableService.createTable(tableDTO);
		return ResponseEntity.ok(saveTable);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<TableModel> updateTable(@PathVariable String id, @RequestBody TableDTO tableDTO){
		TableModel update = tableService.updateTable(id, tableDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(update);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTable(@PathVariable String id){
		tableService.deleteTable(id);
		return ResponseEntity.noContent().build();
	}
	
}
