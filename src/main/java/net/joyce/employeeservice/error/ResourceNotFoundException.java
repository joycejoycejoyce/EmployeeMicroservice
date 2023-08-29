package net.joyce.employeeservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public final static String errorMessage = "Employee with Id = %s can not be found";
    public ResourceNotFoundException(Long id) {
        super(String.format(errorMessage, id));
    }
}
