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
import sasf.net.kevaluacion.dto.enrollment.CreateEnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.EnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.UpdateEnrollmentDto;
import sasf.net.kevaluacion.service.EnrollmentService;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = Constants.ENROLLMENT_TAG_NAME, description = Constants.ENROLLMENT_TAG_DESCRIPTION)
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;
    
    @PostMapping("/sessions/{sessionId}/enrollments")
    @Operation(summary = Constants.ENROLLMENT_CREATE_SUMMARY, description = Constants.ENROLLMENT_CREATE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = Constants.RESPONSE_201_CREATED),
            @ApiResponse(responseCode = "400", description = Constants.EMPLOYEE_ALREADY_ENROLLED + " or " + Constants.SESSION_FULL),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<EnrollmentDto> enrollEmployee(
            @Parameter(description = Constants.PARAM_SESSION_ID, required = true)
            @PathVariable Long sessionId,
            @Validated(ValidationGroups.Create.class) @RequestBody CreateEnrollmentDto createEnrollmentDto) {
        EnrollmentDto enrollmentDto = enrollmentService.enrollEmployee(sessionId, createEnrollmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentDto);
    }
    
    @PutMapping("/enrollments/{enrollmentId}")
    @Operation(summary = Constants.ENROLLMENT_UPDATE_GRADE_SUMMARY, description = Constants.ENROLLMENT_UPDATE_GRADE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_SUCCESS),
            @ApiResponse(responseCode = "400", description = Constants.RESPONSE_400_INVALID_INPUT),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<EnrollmentDto> updateEnrollmentGrade(
            @Parameter(description = Constants.PARAM_ENROLLMENT_ID, required = true)
            @PathVariable Long enrollmentId,
            @Validated(ValidationGroups.Update.class) @RequestBody UpdateEnrollmentDto updateEnrollmentDto) {
        EnrollmentDto enrollmentDto = enrollmentService.updateEnrollmentGrade(enrollmentId, updateEnrollmentDto);
        return ResponseEntity.ok(enrollmentDto);
    }
    
    @GetMapping("/employees/{employeeId}/enrollments")
    @Operation(summary = Constants.ENROLLMENT_GET_BY_EMPLOYEE_SUMMARY, description = Constants.ENROLLMENT_GET_BY_EMPLOYEE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_LIST_RETRIEVED),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<List<EnrollmentDto>> getEmployeeEnrollments(
            @Parameter(description = Constants.PARAM_EMPLOYEE_ID, required = true)
            @PathVariable Long employeeId) {
        List<EnrollmentDto> enrollments = enrollmentService.getEmployeeEnrollments(employeeId);
        return ResponseEntity.ok(enrollments);
    }
    
    @GetMapping("/sessions/{sessionId}/enrollments")
    @Operation(summary = Constants.ENROLLMENT_GET_BY_SESSION_SUMMARY, description = Constants.ENROLLMENT_GET_BY_SESSION_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_LIST_RETRIEVED),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<List<EnrollmentDto>> getSessionEnrollments(
            @Parameter(description = Constants.PARAM_SESSION_ID, required = true)
            @PathVariable Long sessionId) {
        List<EnrollmentDto> enrollments = enrollmentService.getSessionEnrollments(sessionId);
        return ResponseEntity.ok(enrollments);
    }
}
