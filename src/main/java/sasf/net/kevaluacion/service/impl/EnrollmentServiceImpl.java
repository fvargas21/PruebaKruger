package sasf.net.kevaluacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sasf.net.kevaluacion.dto.enrollment.CreateEnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.EnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.UpdateEnrollmentDto;
import sasf.net.kevaluacion.entity.Employee;
import sasf.net.kevaluacion.entity.Enrollment;
import sasf.net.kevaluacion.entity.Session;
import sasf.net.kevaluacion.exception.BusinessException;
import sasf.net.kevaluacion.exception.ResourceNotFoundException;
import sasf.net.kevaluacion.mapper.EnrollmentMapper;
import sasf.net.kevaluacion.repository.EnrollmentRepository;
import sasf.net.kevaluacion.service.EmployeeService;
import sasf.net.kevaluacion.service.EnrollmentService;
import sasf.net.kevaluacion.service.SessionService;
import sasf.net.kevaluacion.util.Constants;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final EmployeeService employeeService;
    private final SessionService sessionService;
    
    public EnrollmentDto enrollEmployee(Long sessionId, CreateEnrollmentDto createEnrollmentDto) {
        log.info("Enrolling employee {} to session {}", createEnrollmentDto.getEmployeeId(), sessionId);
        
        Employee employee = employeeService.findById(createEnrollmentDto.getEmployeeId());
        Session session = sessionService.findById(sessionId);
        
        // Check if employee is already enrolled in this session
        if (enrollmentRepository.existsByEmployeeIdAndSessionId(createEnrollmentDto.getEmployeeId(), sessionId)) {
            throw new BusinessException(Constants.ERROR_ALREADY_ENROLLED, 
                    Constants.EMPLOYEE_ALREADY_ENROLLED);
        }
        
        // Check session capacity
        Long currentEnrollments = enrollmentRepository.countBySessionId(sessionId);
        if (currentEnrollments >= session.getCapacity()) {
            throw new BusinessException(Constants.ERROR_SESSION_FULL, 
                    Constants.SESSION_FULL + " " + session.getCapacity());
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setEmployee(employee);
        enrollment.setSession(session);
        
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        log.info("Employee enrolled successfully with enrollment id: {}", savedEnrollment.getId());
        
        return enrollmentMapper.toDto(savedEnrollment);
    }
    
    public EnrollmentDto updateEnrollmentGrade(Long enrollmentId, UpdateEnrollmentDto updateEnrollmentDto) {
        log.info("Updating grade for enrollment id: {}", enrollmentId);
        
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERROR_ENROLLMENT_NOT_FOUND, 
                        Constants.ENROLLMENT_NOT_FOUND + " ID: " + enrollmentId));
        
        enrollment.setGrade(updateEnrollmentDto.getGrade());
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        
        log.info("Grade updated successfully for enrollment id: {}", enrollmentId);
        return enrollmentMapper.toDto(savedEnrollment);
    }
    
    @Transactional(readOnly = true)
    public List<EnrollmentDto> getEmployeeEnrollments(Long employeeId) {
        log.info("Fetching enrollments for employee id: {}", employeeId);
        
        // Verify employee exists
        employeeService.findById(employeeId);
        
        List<Enrollment> enrollments = enrollmentRepository.findByEmployeeId(employeeId);
        return enrollmentMapper.toDtoList(enrollments);
    }
    
    @Transactional(readOnly = true)
    public List<EnrollmentDto> getSessionEnrollments(Long sessionId) {
        log.info("Fetching enrollments for session id: {}", sessionId);
        
        // Verify session exists
        sessionService.findById(sessionId);
        
        List<Enrollment> enrollments = enrollmentRepository.findBySessionId(sessionId);
        return enrollmentMapper.toDtoList(enrollments);
    }
}
