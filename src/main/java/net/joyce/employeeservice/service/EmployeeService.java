package net.joyce.employeeservice.service;

import net.joyce.employeeservice.dto.EmployeeDTO;

public interface EmployeeService {
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    public EmployeeDTO getEmployeeById(Long employeeId);
}
