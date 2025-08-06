package sasf.net.kevaluacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sasf.net.kevaluacion.dto.employee.CreateEmployeeDto;
import sasf.net.kevaluacion.dto.employee.EmployeeDto;
import sasf.net.kevaluacion.dto.employee.UpdateEmployeeDto;
import sasf.net.kevaluacion.exception.ResourceNotFoundException;
import sasf.net.kevaluacion.service.EmployeeService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@DisplayName("Employee Controller Tests")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDto employeeDto;
    private CreateEmployeeDto createEmployeeDto;
    private UpdateEmployeeDto updateEmployeeDto;

    @BeforeEach
    void setUp() {
        employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFullName("Juan Pérez");
        employeeDto.setEmail("juan.perez@kevaluacion.com");

        createEmployeeDto = new CreateEmployeeDto();
        createEmployeeDto.setFullName("Juan Pérez");
        createEmployeeDto.setEmail("juan.perez@kevaluacion.com");

        updateEmployeeDto = new UpdateEmployeeDto();
        updateEmployeeDto.setFullName("Juan Pérez Updated");
        updateEmployeeDto.setEmail("juan.updated@kevaluacion.com");
    }

    @Test
    @DisplayName("Should create employee successfully")
    void createEmployee_Success() throws Exception {
        // Given
        when(employeeService.createEmployee(any(CreateEmployeeDto.class)))
                .thenReturn(employeeDto);

        // When & Then
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEmployeeDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fullName", is("Juan Pérez")))
                .andExpect(jsonPath("$.email", is("juan.perez@kevaluacion.com")));
    }

    @Test
    @DisplayName("Should return validation error when creating employee with invalid data")
    void createEmployee_ValidationError() throws Exception {
        // Given
        CreateEmployeeDto invalidDto = new CreateEmployeeDto();
        invalidDto.setFullName(""); // Invalid empty name
        invalidDto.setEmail("invalid-email"); // Invalid email format

        // When & Then
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get all employees successfully")
    void getAllEmployees_Success() throws Exception {
        // Given
        EmployeeDto employee2 = new EmployeeDto();
        employee2.setId(2L);
        employee2.setFullName("María García");
        employee2.setEmail("maria.garcia@kevaluacion.com");

        List<EmployeeDto> employees = Arrays.asList(employeeDto, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].fullName", is("Juan Pérez")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].fullName", is("María García")));
    }

    @Test
    @DisplayName("Should get employee by id successfully")
    void getEmployeeById_Success() throws Exception {
        // Given
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeDto);

        // When & Then
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fullName", is("Juan Pérez")))
                .andExpect(jsonPath("$.email", is("juan.perez@kevaluacion.com")));
    }

    @Test
    @DisplayName("Should return 404 when employee not found")
    void getEmployeeById_NotFound() throws Exception {
        // Given
        when(employeeService.getEmployeeById(999L))
                .thenThrow(new ResourceNotFoundException("EMPLOYEE_NOT_FOUND", "Employee not found"));

        // When & Then
        mockMvc.perform(get("/employees/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update employee successfully")
    void updateEmployee_Success() throws Exception {
        // Given
        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setId(1L);
        updatedEmployee.setFullName("Juan Pérez Updated");
        updatedEmployee.setEmail("juan.updated@kevaluacion.com");

        when(employeeService.updateEmployee(eq(1L), any(UpdateEmployeeDto.class)))
                .thenReturn(updatedEmployee);

        // When & Then
        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEmployeeDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fullName", is("Juan Pérez Updated")))
                .andExpect(jsonPath("$.email", is("juan.updated@kevaluacion.com")));
    }

    @Test
    @DisplayName("Should return validation error when updating employee with invalid data")
    void updateEmployee_ValidationError() throws Exception {
        // Given
        UpdateEmployeeDto invalidDto = new UpdateEmployeeDto();
        invalidDto.setFullName(""); // Invalid empty name
        invalidDto.setEmail("invalid-email"); // Invalid email format

        // When & Then
        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent employee")
    void updateEmployee_NotFound() throws Exception {
        // Given
        when(employeeService.updateEmployee(eq(999L), any(UpdateEmployeeDto.class)))
                .thenThrow(new ResourceNotFoundException("EMPLOYEE_NOT_FOUND", "Employee not found"));

        // When & Then
        mockMvc.perform(put("/employees/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEmployeeDto)))
                .andExpect(status().isNotFound());
    }
}
