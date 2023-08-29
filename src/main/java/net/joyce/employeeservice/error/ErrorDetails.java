package net.joyce.employeeservice.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String path;
    private String description;
}
