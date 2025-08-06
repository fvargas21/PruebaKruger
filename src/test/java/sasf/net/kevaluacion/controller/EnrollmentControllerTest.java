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
import sasf.net.kevaluacion.dto.enrollment.CreateEnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.EnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.UpdateEnrollmentDto;
import sasf.net.kevaluacion.exception.BusinessException;
import sasf.net.kevaluacion.exception.ResourceNotFoundException;
import sasf.net.kevaluacion.service.EnrollmentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrollmentController.class)
@DisplayName("Enrollment Controller Tests")
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private EnrollmentDto enrollmentDto;
    private CreateEnrollmentDto createEnrollmentDto;
    private UpdateEnrollmentDto updateEnrollmentDto;

    @BeforeEach
    void setUp() {
        enrollmentDto = new EnrollmentDto();
        enrollmentDto.setId(1L);
        enrollmentDto.setEmployeeId(1L);
        enrollmentDto.setEmployeeName("Juan Pérez");
        enrollmentDto.setEmployeeEmail("juan.perez@kevaluacion.com");
        enrollmentDto.setSessionId(1L);
        enrollmentDto.setCourseName("Spring Boot Fundamentals");
        enrollmentDto.setSessionDate(LocalDate.now().plusDays(7));
        enrollmentDto.setGrade(8.5);

        createEnrollmentDto = new CreateEnrollmentDto();
        createEnrollmentDto.setEmployeeId(1L);

        updateEnrollmentDto = new UpdateEnrollmentDto();
        updateEnrollmentDto.setGrade(9.0);
    }

    @Test
    @DisplayName("Should enroll employee in session successfully")
    void enrollEmployee_Success() throws Exception {
        // Given
        when(enrollmentService.enrollEmployee(eq(1L), any(CreateEnrollmentDto.class)))
                .thenReturn(enrollmentDto);

        // When & Then
        mockMvc.perform(post("/sessions/1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEnrollmentDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.employeeName", is("Juan Pérez")))
                .andExpect(jsonPath("$.sessionId", is(1)))
                .andExpect(jsonPath("$.grade", is(8.5)));
    }

    @Test
    @DisplayName("Should return validation error when enrolling with invalid data")
    void enrollEmployee_ValidationError() throws Exception {
        // Given
        CreateEnrollmentDto invalidDto = new CreateEnrollmentDto();
        invalidDto.setEmployeeId(null); // Invalid null employee ID

        // When & Then
        mockMvc.perform(post("/sessions/1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when employee already enrolled")
    void enrollEmployee_AlreadyEnrolled() throws Exception {
        // Given
        when(enrollmentService.enrollEmployee(eq(1L), any(CreateEnrollmentDto.class)))
                .thenThrow(new BusinessException("ALREADY_ENROLLED", "Employee already enrolled"));

        // When & Then
        mockMvc.perform(post("/sessions/1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEnrollmentDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when session is full")
    void enrollEmployee_SessionFull() throws Exception {
        // Given
        when(enrollmentService.enrollEmployee(eq(1L), any(CreateEnrollmentDto.class)))
                .thenThrow(new BusinessException("SESSION_FULL", "Session is full"));

        // When & Then
        mockMvc.perform(post("/sessions/1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEnrollmentDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get employee enrollments successfully")
    void getEmployeeEnrollments_Success() throws Exception {
        // Given
        EnrollmentDto enrollment2 = new EnrollmentDto();
        enrollment2.setId(2L);
        enrollment2.setEmployeeId(1L);
        enrollment2.setEmployeeName("Juan Pérez");
        enrollment2.setEmployeeEmail("juan.perez@kevaluacion.com");
        enrollment2.setSessionId(2L);
        enrollment2.setCourseName("Advanced Spring Boot");
        enrollment2.setSessionDate(LocalDate.now().plusDays(14));
        enrollment2.setGrade(7.5);

        List<EnrollmentDto> enrollments = Arrays.asList(enrollmentDto, enrollment2);
        when(enrollmentService.getEmployeeEnrollments(1L)).thenReturn(enrollments);

        // When & Then
        mockMvc.perform(get("/employees/1/enrollments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].grade", is(8.5)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].grade", is(7.5)));
    }

    @Test
    @DisplayName("Should get session enrollments successfully")
    void getSessionEnrollments_Success() throws Exception {
        // Given
        EnrollmentDto enrollment2 = new EnrollmentDto();
        enrollment2.setId(2L);
        enrollment2.setEmployeeId(2L);
        enrollment2.setEmployeeName("María García");
        enrollment2.setEmployeeEmail("maria.garcia@kevaluacion.com");
        enrollment2.setSessionId(1L);
        enrollment2.setCourseName("Spring Boot Fundamentals");
        enrollment2.setSessionDate(LocalDate.now().plusDays(7));
        enrollment2.setGrade(9.0);

        List<EnrollmentDto> enrollments = Arrays.asList(enrollmentDto, enrollment2);
        when(enrollmentService.getSessionEnrollments(1L)).thenReturn(enrollments);

        // When & Then
        mockMvc.perform(get("/sessions/1/enrollments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeName", is("Juan Pérez")))
                .andExpect(jsonPath("$[1].employeeName", is("María García")));
    }

    @Test
    @DisplayName("Should update enrollment grade successfully")
    void updateEnrollmentGrade_Success() throws Exception {
        // Given
        EnrollmentDto updatedEnrollment = new EnrollmentDto();
        updatedEnrollment.setId(1L);
        updatedEnrollment.setEmployeeId(1L);
        updatedEnrollment.setEmployeeName("Juan Pérez");
        updatedEnrollment.setEmployeeEmail("juan.perez@kevaluacion.com");
        updatedEnrollment.setSessionId(1L);
        updatedEnrollment.setCourseName("Spring Boot Fundamentals");
        updatedEnrollment.setSessionDate(LocalDate.now().plusDays(7));
        updatedEnrollment.setGrade(9.0);

        when(enrollmentService.updateEnrollmentGrade(eq(1L), any(UpdateEnrollmentDto.class)))
                .thenReturn(updatedEnrollment);

        // When & Then
        mockMvc.perform(put("/enrollments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEnrollmentDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.grade", is(9.0)));
    }

    @Test
    @DisplayName("Should return validation error when updating grade with invalid data")
    void updateEnrollmentGrade_ValidationError() throws Exception {
        // Given
        UpdateEnrollmentDto invalidDto = new UpdateEnrollmentDto();
        invalidDto.setGrade(11.0); // Grade above maximum (10.0)

        // When & Then
        mockMvc.perform(put("/enrollments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent enrollment")
    void updateEnrollmentGrade_NotFound() throws Exception {
        // Given
        when(enrollmentService.updateEnrollmentGrade(eq(999L), any(UpdateEnrollmentDto.class)))
                .thenThrow(new ResourceNotFoundException("ENROLLMENT_NOT_FOUND", "Enrollment not found"));

        // When & Then
        mockMvc.perform(put("/enrollments/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEnrollmentDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when getting enrollments for non-existent employee")
    void getEmployeeEnrollments_EmployeeNotFound() throws Exception {
        // Given
        when(enrollmentService.getEmployeeEnrollments(999L))
                .thenThrow(new ResourceNotFoundException("EMPLOYEE_NOT_FOUND", "Employee not found"));

        // When & Then
        mockMvc.perform(get("/employees/999/enrollments"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when getting enrollments for non-existent session")
    void getSessionEnrollments_SessionNotFound() throws Exception {
        // Given
        when(enrollmentService.getSessionEnrollments(999L))
                .thenThrow(new ResourceNotFoundException("SESSION_NOT_FOUND", "Session not found"));

        // When & Then
        mockMvc.perform(get("/sessions/999/enrollments"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when enrolling in non-existent session")
    void enrollEmployee_SessionNotFound() throws Exception {
        // Given
        when(enrollmentService.enrollEmployee(eq(999L), any(CreateEnrollmentDto.class)))
                .thenThrow(new ResourceNotFoundException("SESSION_NOT_FOUND", "Session not found"));

        // When & Then
        mockMvc.perform(post("/sessions/999/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEnrollmentDto)))
                .andExpect(status().isNotFound());
    }
}
