package net.joyce.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.joyce.employeeservice.dto.EmployeeDTO;
import net.joyce.employeeservice.entity.Employee;
import net.joyce.employeeservice.error.ResourceNotFoundException;
import net.joyce.employeeservice.repository.EmployeeRepository;
import net.joyce.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        // DTO -> Entity
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        Employee save = employeeRepository.save(employee);

        // Employee Entity -> DTO
        EmployeeDTO result = modelMapper.map(save, EmployeeDTO.class);

        return result;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        Optional<Employee> byId = employeeRepository.findById(employeeId);

        // 如果没有就 throw exception
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException(employeeId);
        }

        Employee employee = byId.get();

        // Entity -> DTO
        EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
        return dto;
    }
}
