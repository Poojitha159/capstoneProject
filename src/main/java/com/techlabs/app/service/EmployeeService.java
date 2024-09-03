package com.techlabs.app.service;

import com.techlabs.app.dto.AgentRequestDto;

import jakarta.validation.Valid;

public interface EmployeeService {

	String registerAgent(@Valid AgentRequestDto agentRequestDto);

}
