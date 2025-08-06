package sasf.net.kevaluacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sasf.net.kevaluacion.dto.employee.CreateEmployeeDto;
import sasf.net.kevaluacion.dto.employee.EmployeeDto;
import sasf.net.kevaluacion.dto.employee.UpdateEmployeeDto;
import sasf.net.kevaluacion.entity.Employee;
import sasf.net.kevaluacion.exception.BusinessException;
import sasf.net.kevaluacion.exception.ResourceNotFoundException;
import sasf.net.kevaluacion.mapper.EmployeeMapper;
import sasf.net.kevaluacion.repository.EmployeeRepository;
import sasf.net.kevaluacion.service.EmployeeService;
import sasf.net.kevaluacion.util.Constants;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    
    public EmployeeDto createEmployee(CreateEmployeeDto createEmployeeDto) {
        log.info("Creating employee with email: {}", createEmployeeDto.getEmail());
        
        if (employeeRepository.existsByEmail(createEmployeeDto.getEmail())) {
            throw new BusinessException(Constants.ERROR_EMAIL_ALREADY_EXISTS, 
                    "Employee with email " + createEmployeeDto.getEmail() + " already exists");
        }
        
        try {
            Employee employee = employeeMapper.toEntity(createEmployeeDto);
            Employee savedEmployee = employeeRepository.save(employee);
            log.info("Employee created successfully with id: {}", savedEmployee.getId());
            return employeeMapper.toDto(savedEmployee);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(Constants.ERROR_EMAIL_ALREADY_EXISTS, 
                    "Employee with email " + createEmployeeDto.getEmail() + " already exists");
        }
    }
    
    public EmployeeDto updateEmployee(Long employeeId, UpdateEmployeeDto updateEmployeeDto) {
        log.info("Updating employee with id: {}", employeeId);
        
        Employee employee = findById(employeeId);
        
        // Check if email already exists for another employee
        if (!employee.getEmail().equals(updateEmployeeDto.getEmail()) && 
            employeeRepository.existsByEmail(updateEmployeeDto.getEmail())) {
            throw new BusinessException(Constants.ERROR_EMAIL_ALREADY_EXISTS, 
                    "Employee with email " + updateEmployeeDto.getEmail() + " already exists");
        }
        
        try {
            employee.setFullName(updateEmployeeDto.getFullName());
            employee.setEmail(updateEmployeeDto.getEmail());
            Employee savedEmployee = employeeRepository.save(employee);
            log.info("Employee updated successfully with id: {}", savedEmployee.getId());
            return employeeMapper.toDto(savedEmployee);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(Constants.ERROR_EMAIL_ALREADY_EXISTS, 
                    "Employee with email " + updateEmployeeDto.getEmail() + " already exists");
        }
    }
    
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        log.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toDtoList(employees);
    }
    
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERROR_EMPLOYEE_NOT_FOUND, 
                        Constants.EMPLOYEE_NOT_FOUND + " ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = findById(id);
        return employeeMapper.toDto(employee);
    }
}
