package sasf.net.kevaluacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sasf.net.kevaluacion.dto.session.CreateSessionDto;
import sasf.net.kevaluacion.dto.session.SessionDto;
import sasf.net.kevaluacion.dto.session.UpdateSessionDto;
import sasf.net.kevaluacion.entity.Course;
import sasf.net.kevaluacion.entity.Session;
import sasf.net.kevaluacion.exception.BusinessException;
import sasf.net.kevaluacion.exception.ResourceNotFoundException;
import sasf.net.kevaluacion.mapper.SessionMapper;
import sasf.net.kevaluacion.repository.EnrollmentRepository;
import sasf.net.kevaluacion.repository.SessionRepository;
import sasf.net.kevaluacion.service.CourseService;
import sasf.net.kevaluacion.service.SessionService;
import sasf.net.kevaluacion.util.Constants;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SessionServiceImpl implements SessionService {
    
    private final SessionRepository sessionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SessionMapper sessionMapper;
    private final CourseService courseService;
    
    public SessionDto createSession(Long courseId, CreateSessionDto createSessionDto) {
        log.info("Creating session for course id: {} on date: {}", courseId, createSessionDto.getScheduledDate());
        
        Course course = courseService.findById(courseId);
        
        if (createSessionDto.getCapacity() > course.getMaxCapacity()) {
            throw new BusinessException(Constants.ERROR_INVALID_CAPACITY, 
                    Constants.CAPACITY_EXCEEDS_LIMIT + " " + course.getMaxCapacity());
        }
        
        Session session = sessionMapper.toEntity(createSessionDto);
        session.setCourse(course);
        Session savedSession = sessionRepository.save(session);
        
        log.info("Session created successfully with id: {}", savedSession.getId());
        
        SessionDto sessionDto = sessionMapper.toDto(savedSession);
        sessionDto.setEnrolledCount(0);
        return sessionDto;
    }
    
    public SessionDto updateSession(Long sessionId, UpdateSessionDto updateSessionDto) {
        log.info("Updating session with id: {}", sessionId);
        
        Session session = findById(sessionId);
        
        // Validate capacity doesn't exceed course max capacity
        if (updateSessionDto.getCapacity() > session.getCourse().getMaxCapacity()) {
            throw new BusinessException(Constants.ERROR_INVALID_CAPACITY, 
                    Constants.CAPACITY_EXCEEDS_LIMIT + " " + session.getCourse().getMaxCapacity());
        }
        
        // Validate capacity is not less than current enrollments
        Long currentEnrollments = enrollmentRepository.countBySessionId(sessionId);
        if (updateSessionDto.getCapacity() < currentEnrollments) {
            throw new BusinessException(Constants.ERROR_INVALID_CAPACITY, 
                    "Session capacity cannot be less than current enrollments (" + currentEnrollments + ")");
        }
        
        session.setScheduledDate(updateSessionDto.getScheduledDate());
        session.setCapacity(updateSessionDto.getCapacity());
        
        Session savedSession = sessionRepository.save(session);
        log.info("Session updated successfully with id: {}", savedSession.getId());
        
        SessionDto sessionDto = sessionMapper.toDto(savedSession);
        sessionDto.setEnrolledCount(currentEnrollments.intValue());
        return sessionDto;
    }
    
    @Transactional(readOnly = true)
    public Session findById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERROR_SESSION_NOT_FOUND, 
                        Constants.SESSION_NOT_FOUND + " ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public SessionDto getSessionById(Long id) {
        Session session = findById(id);
        SessionDto sessionDto = sessionMapper.toDto(session);
        Long enrolledCount = enrollmentRepository.countBySessionId(id);
        sessionDto.setEnrolledCount(enrolledCount.intValue());
        return sessionDto;
    }
    
    @Transactional(readOnly = true)
    public List<SessionDto> getSessionsByCourseId(Long courseId) {
        log.info("Fetching sessions for course id: {}", courseId);
        List<Session> sessions = sessionRepository.findByCourseId(courseId);
        List<SessionDto> sessionDtos = sessionMapper.toDtoList(sessions);
        
        // Set enrolled count for each session
        sessionDtos.forEach(sessionDto -> {
            Long enrolledCount = enrollmentRepository.countBySessionId(sessionDto.getId());
            sessionDto.setEnrolledCount(enrolledCount.intValue());
        });
        
        return sessionDtos;
    }
}
