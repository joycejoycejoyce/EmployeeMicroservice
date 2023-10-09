package net.joyce.employeeservice.service;

import net.joyce.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="organizationAPIClient", url = "http://localhost:8083/api/organizations")
public interface OrganizationAPIClient {
    @GetMapping("{code}")
    OrganizationDto getOrganization(@PathVariable String code);
}
