package net.joyce.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* 就是既有 employee 的信息，也有 department 的信息
* */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDTO {
    private EmployeeDTO employeeDTO;
    private DepartmentDTO departmentDTO;
    private OrganizationDto organizationDto;
}
