package sasf.net.kevaluacion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sasf.net.kevaluacion.dto.enrollment.CreateEnrollmentDto;
import sasf.net.kevaluacion.dto.enrollment.EnrollmentDto;
import sasf.net.kevaluacion.entity.Enrollment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "session", ignore = true)
    @Mapping(target = "grade", ignore = true)
    Enrollment toEntity(CreateEnrollmentDto createEnrollmentDto);
    
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeName")
    @Mapping(source = "employee.email", target = "employeeEmail")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.course.name", target = "courseName")
    @Mapping(source = "session.scheduledDate", target = "sessionDate")
    EnrollmentDto toDto(Enrollment enrollment);
    
    List<EnrollmentDto> toDtoList(List<Enrollment> enrollments);
}
