package net.joyce.employeeservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.joyce.employeeservice.dto.APIResponseDTO;
import net.joyce.employeeservice.dto.DepartmentDTO;
import net.joyce.employeeservice.dto.EmployeeDTO;
import net.joyce.employeeservice.entity.Employee;
import net.joyce.employeeservice.error.ResourceNotFoundException;
import net.joyce.employeeservice.repository.EmployeeRepository;
import net.joyce.employeeservice.service.APIClient;
import net.joyce.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;

@Service

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ModelMapper modelMapper;
    // open Feign, 用来 communicate between microservices 的
    @Autowired
    private APIClient apiClient;
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        // DTO -> Entity
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        Employee save = employeeRepository.save(employee);

        // Employee Entity -> DTO
        EmployeeDTO result = modelMapper.map(save, EmployeeDTO.class);

        return result;
    }
    /* 把 CircuitBreak name 和 Project name 设置成一样的
    *
    * */
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDTO getEmployeeById(Long employeeId) {
        Optional<Employee> byId = employeeRepository.findById(employeeId);

        // 如果没有就 throw exception
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException(employeeId);
        }

        Employee employee = byId.get();
        // 拿到 employee 的 department 信息
        DepartmentDTO departmentDTO = fetchDepartment(employee.getDepartmentCode());
        // Entity -> DTO
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return new APIResponseDTO(employeeDTO, departmentDTO);
    }

    private DepartmentDTO fetchDepartment(String departmentCode) {
        // 去 call API 里面的方法
        DepartmentDTO departmentDTO = apiClient.getDepartment(departmentCode);
        return departmentDTO;
    }

    public APIResponseDTO getDefaultDepartment(Long employeeId, Exception exception) {
        Optional<Employee> repo = employeeRepository.findById(employeeId);
        if (repo.isEmpty()) {
            throw new ResourceNotFoundException(employeeId);
        }
        Employee employee = repo.get();


        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        DepartmentDTO departmentDTO = createDefaultDepartmentDTO();

        APIResponseDTO apiResponseDTO = new APIResponseDTO();
        apiResponseDTO.setEmployeeDTO(employeeDTO);
        apiResponseDTO.setDepartmentDTO(departmentDTO);
        return apiResponseDTO;
    }

    private DepartmentDTO createDefaultDepartmentDTO() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentName("General Department");
        dto.setDepartmentCode("GD001");
        dto.setDepartmentDescription("General Department of the company");
        return dto;
    }
}
