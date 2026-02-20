package org.example.employeemanagementsystem.repository;

import org.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Boolean existsByFirstNameContainsAndLastNameContainingAndEmailContaining(String firstName, String lastName, String email);
}
