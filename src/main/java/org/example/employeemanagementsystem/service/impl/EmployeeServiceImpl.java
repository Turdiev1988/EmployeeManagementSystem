package org.example.employeemanagementsystem.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.employeemanagementsystem.dto.EmployeeDto;
import org.example.employeemanagementsystem.entity.Employee;
import org.example.employeemanagementsystem.exception.ResourceExistsException;
import org.example.employeemanagementsystem.exception.ResourceNotFoundException;
import org.example.employeemanagementsystem.mapper.EmployeeMapper;
import org.example.employeemanagementsystem.repository.EmployeeRepository;
import org.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;


    private Employee findEmployeeByIdOrThrow(Long id) throws ResourceNotFoundException{
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee is not exists with given id: " + id));
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (repository.existsByFirstNameContainsAndLastNameContainingAndEmailContaining(
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail())){
            log.warn("Duplicate entry attempted for Employee ");
            throw new ResourceExistsException("This information :"+
                    employeeDto.getFirstName()+","+
                    employeeDto.getLastName()+","+
                    employeeDto.getEmail()+" available in the database!");
        }
        Employee employee = EmployeeMapper.INSTANCE.mapToEntity(employeeDto);

        Employee savedEmployee = repository.save(employee);
        return EmployeeMapper.INSTANCE.mapToDto(savedEmployee);


    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) throws ResourceNotFoundException{
        Employee employee = findEmployeeByIdOrThrow(employeeId);
        return EmployeeMapper.INSTANCE.mapToDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = repository.findAll();
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee)throws ResourceNotFoundException {
        Employee employee = findEmployeeByIdOrThrow(employeeId);
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updated = repository.save(employee);
        return EmployeeMapper.INSTANCE.mapToDto(updated);
    }

    @Override
    public void deleteEmployee(Long employeeId)throws ResourceNotFoundException {
        Employee employee = findEmployeeByIdOrThrow(employeeId);
        repository.delete(employee);
    }

}
