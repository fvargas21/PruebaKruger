package sasf.net.kevaluacion.service;

import sasf.net.kevaluacion.dto.employee.CreateEmployeeDto;
import sasf.net.kevaluacion.dto.employee.EmployeeDto;
import sasf.net.kevaluacion.dto.employee.UpdateEmployeeDto;
import sasf.net.kevaluacion.entity.Employee;

import java.util.List;

public interface EmployeeService {
    
    EmployeeDto createEmployee(CreateEmployeeDto createEmployeeDto);
    
    EmployeeDto updateEmployee(Long employeeId, UpdateEmployeeDto updateEmployeeDto);
    
    List<EmployeeDto> getAllEmployees();
    
    Employee findById(Long id);
    
    EmployeeDto getEmployeeById(Long id);
}
