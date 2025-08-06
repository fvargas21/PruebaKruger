package sasf.net.kevaluacion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sasf.net.kevaluacion.dto.session.CreateSessionDto;
import sasf.net.kevaluacion.dto.session.SessionDto;
import sasf.net.kevaluacion.dto.session.UpdateSessionDto;
import sasf.net.kevaluacion.entity.Session;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "enrolledEmployees", ignore = true)
    Session toEntity(CreateSessionDto createSessionDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "enrolledEmployees", ignore = true)
    Session toEntity(UpdateSessionDto updateSessionDto);
    
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    @Mapping(target = "enrolledCount", ignore = true)
    SessionDto toDto(Session session);
    
    List<SessionDto> toDtoList(List<Session> sessions);
}
