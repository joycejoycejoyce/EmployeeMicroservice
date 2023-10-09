package net.joyce.employeeservice.service;

import net.joyce.employeeservice.dto.DepartmentDTO;
import net.joyce.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/* 这个 @FeignClient 会直接把这个 interface 变成一个 feign client
* feign client library 会动态的创造一个 对于这个 interface 的 implementation
*
* 在 parameter 里面放上 Service ID, eureka 会自动的帮你匹配一个 available instance
* */
@FeignClient(name = "DEPARTMENT-SERVICE")
public interface APIClient {
    @GetMapping("api/departments/{department-code}")
    DepartmentDTO getDepartment(@PathVariable("department-code") String departmentCode);
}
