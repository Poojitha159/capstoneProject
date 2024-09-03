package com.techlabs.app.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.AgentRequestDto;
import com.techlabs.app.entity.Agent;
import com.techlabs.app.entity.City;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.APIException;
import com.techlabs.app.repository.AgentRepository;
import com.techlabs.app.repository.CityRepository;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;
    

	@Override
	public String registerAgent(@Valid AgentRequestDto agentRequestDto) {
		
		 // Validate that the password is not null
        if (agentRequestDto.getPassword() == null || agentRequestDto.getPassword().isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Password must not be null or empty");
        }

        // Create and save User
        User user = new User();
        user.setUsername(agentRequestDto.getUsername());
        user.setEmail(agentRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(agentRequestDto.getPassword()));
       // userRepository.save(user);
       
        Role agentRole = roleRepository.findByName("ROLE_AGENT")
              .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Role not found: ROLE_AGENT"));
      Set<Role> roles = new HashSet<>();
      roles.add(agentRole);
      user.setRoles(roles);
      User savedUser = userRepository.save(user);
        // Find and set City
        City city = cityRepository.findById(agentRequestDto.getCity_id())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "City not found with id: " + agentRequestDto.getCity_id()));

        // Create and save Agent
        Agent agent = new Agent();
        agent.setAgentId(savedUser.getId());
        agent.setUser(savedUser);
        agent.setFirstName(agentRequestDto.getFirstName());
        agent.setLastName(agentRequestDto.getLastName());
        agent.setPhoneNumber(agentRequestDto.getPhoneNumber());
        agent.setCity(city);
        agent.setActive(agentRequestDto.isActive());
        agent.setVerified(false); // Set isVerified to false by default
        agentRepository.save(agent);

        return "Agent registered successfully";
    }
	}


