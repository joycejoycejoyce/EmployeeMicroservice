package net.joyce.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.joyce.employeeservice.dto.APIResponseDTO;
import net.joyce.employeeservice.dto.DepartmentDTO;
import net.joyce.employeeservice.dto.EmployeeDTO;
import net.joyce.employeeservice.entity.Employee;
import net.joyce.employeeservice.error.ResourceNotFoundException;
import net.joyce.employeeservice.repository.EmployeeRepository;
import net.joyce.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private WebClient webClient;
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
        // call 到这个给的 url, 然后返回一个 DepartmentDTO object
        DepartmentDTO departmentDTO = webClient.get()
                        .uri("http://localhost:8080/api/departments/" + departmentCode)
                .retrieve()
                .bodyToMono(DepartmentDTO.class)
                .block();
        return departmentDTO;
    }
}
