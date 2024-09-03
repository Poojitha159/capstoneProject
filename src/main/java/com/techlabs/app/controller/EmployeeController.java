package com.techlabs.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.AgentRequestDto;
import com.techlabs.app.service.AdminService;
import com.techlabs.app.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/E-Insurance/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {
	 @Autowired
	    private EmployeeService employeeService;
	 
	

	    @PostMapping("/registerAgent")
	    public ResponseEntity<String> registerAgent(@RequestBody @Valid AgentRequestDto agentRequestDto) {
	      System.out.println(agentRequestDto);
	        String response = employeeService.registerAgent(agentRequestDto);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	   
	}
