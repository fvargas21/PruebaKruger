package sasf.net.kevaluacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sasf.net.kevaluacion.dto.employee.CreateEmployeeDto;
import sasf.net.kevaluacion.dto.employee.EmployeeDto;
import sasf.net.kevaluacion.dto.employee.UpdateEmployeeDto;
import sasf.net.kevaluacion.service.EmployeeService;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = Constants.EMPLOYEE_TAG_NAME, description = Constants.EMPLOYEE_TAG_DESCRIPTION)
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    @PostMapping
    @Operation(summary = Constants.EMPLOYEE_CREATE_SUMMARY, description = Constants.EMPLOYEE_CREATE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = Constants.RESPONSE_201_CREATED),
            @ApiResponse(responseCode = "400", description = Constants.RESPONSE_400_INVALID_INPUT),
            @ApiResponse(responseCode = "409", description = Constants.EMPLOYEE_EMAIL_ALREADY_EXISTS)
    })
    public ResponseEntity<EmployeeDto> createEmployee(
            @Validated(ValidationGroups.Create.class) @RequestBody CreateEmployeeDto createEmployeeDto) {
        EmployeeDto employeeDto = employeeService.createEmployee(createEmployeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto);
    }
    
    @GetMapping
    @Operation(summary = Constants.EMPLOYEE_GET_ALL_SUMMARY, description = Constants.EMPLOYEE_GET_ALL_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_LIST_RETRIEVED)
    })
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/{employeeId}")
    @Operation(summary = Constants.EMPLOYEE_GET_BY_ID_SUMMARY, description = Constants.EMPLOYEE_GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_SUCCESS),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @Parameter(description = Constants.PARAM_EMPLOYEE_ID, required = true)
            @PathVariable Long employeeId) {
        EmployeeDto employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }
    
    @PutMapping("/{employeeId}")
    @Operation(summary = Constants.EMPLOYEE_UPDATE_SUMMARY, description = Constants.EMPLOYEE_UPDATE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_SUCCESS),
            @ApiResponse(responseCode = "400", description = Constants.RESPONSE_400_INVALID_INPUT),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND),
            @ApiResponse(responseCode = "409", description = Constants.EMPLOYEE_EMAIL_ALREADY_EXISTS)
    })
    public ResponseEntity<EmployeeDto> updateEmployee(
            @Parameter(description = Constants.PARAM_EMPLOYEE_ID, required = true)
            @PathVariable Long employeeId,
            @Validated(ValidationGroups.Update.class) @RequestBody UpdateEmployeeDto updateEmployeeDto) {
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updateEmployeeDto);
        return ResponseEntity.ok(employeeDto);
    }
}
