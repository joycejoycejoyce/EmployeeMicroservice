package net.joyce.employeeservice.controller;

import lombok.AllArgsConstructor;
import net.joyce.employeeservice.dto.APIResponseDTO;
import net.joyce.employeeservice.dto.EmployeeDTO;
import net.joyce.employeeservice.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO saved = employeeService.saveEmployee(employeeDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<APIResponseDTO> getEmployee(@PathVariable Long id) {
        APIResponseDTO dto = employeeService.getEmployeeById(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
