package sasf.net.kevaluacion.service;

import sasf.net.kevaluacion.dto.session.CreateSessionDto;
import sasf.net.kevaluacion.dto.session.SessionDto;
import sasf.net.kevaluacion.dto.session.UpdateSessionDto;
import sasf.net.kevaluacion.entity.Session;

import java.util.List;

public interface SessionService {
    
    SessionDto createSession(Long courseId, CreateSessionDto createSessionDto);
    
    SessionDto updateSession(Long sessionId, UpdateSessionDto updateSessionDto);
    
    Session findById(Long id);
    
    SessionDto getSessionById(Long id);
    
    List<SessionDto> getSessionsByCourseId(Long courseId);
}
