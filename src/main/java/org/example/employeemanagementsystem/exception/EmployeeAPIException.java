package org.example.employeemanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EmployeeAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;
}
