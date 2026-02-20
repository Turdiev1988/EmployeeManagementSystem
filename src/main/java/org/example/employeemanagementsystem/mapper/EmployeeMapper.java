package org.example.employeemanagementsystem.mapper;

import org.example.employeemanagementsystem.dto.EmployeeDto;
import org.example.employeemanagementsystem.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    EmployeeDto mapToDto(Employee employee);
    Employee mapToEntity(EmployeeDto employeeDto);
}
