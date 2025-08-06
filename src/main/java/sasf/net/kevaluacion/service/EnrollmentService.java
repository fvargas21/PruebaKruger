package sasf.net.kevaluacion.service;

import sasf.net.kevaluacion.dto.enrollment.CreateEnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.EnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.UpdateEnrollmentDto;

import java.util.List;

public interface EnrollmentService {
    
    EnrollmentDto enrollEmployee(Long sessionId, CreateEnrollmentDto createEnrollmentDto);
    
    EnrollmentDto updateEnrollmentGrade(Long enrollmentId, UpdateEnrollmentDto updateEnrollmentDto);
    
    List<EnrollmentDto> getEmployeeEnrollments(Long employeeId);
    
    List<EnrollmentDto> getSessionEnrollments(Long sessionId);
}
