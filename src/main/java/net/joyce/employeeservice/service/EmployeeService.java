package net.joyce.employeeservice.service;

import net.joyce.employeeservice.dto.APIResponseDTO;
import net.joyce.employeeservice.dto.EmployeeDTO;

public interface EmployeeService {
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    public APIResponseDTO getEmployeeById(Long employeeId);
}
