package sasf.net.kevaluacion.mapper;

import org.mapstruct.Mapper;
import sasf.net.kevaluacion.dto.employee.CreateEmployeeDto;
import sasf.net.kevaluacion.dto.employee.EmployeeDto;
import sasf.net.kevaluacion.dto.employee.UpdateEmployeeDto;
import sasf.net.kevaluacion.entity.Employee;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    
    Employee toEntity(CreateEmployeeDto createEmployeeDto);
    
    Employee toEntity(UpdateEmployeeDto updateEmployeeDto);
    
    EmployeeDto toDto(Employee employee);
    
    List<EmployeeDto> toDtoList(List<Employee> employees);
}
