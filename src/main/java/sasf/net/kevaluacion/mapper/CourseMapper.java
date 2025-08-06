package sasf.net.kevaluacion.mapper;

import org.mapstruct.Mapper;
import sasf.net.kevaluacion.dto.course.CreateCourseDto;
import sasf.net.kevaluacion.dto.course.CourseDto;
import sasf.net.kevaluacion.dto.course.UpdateCourseDto;
import sasf.net.kevaluacion.entity.Course;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    
    Course toEntity(CreateCourseDto createCourseDto);
    
    Course toEntity(UpdateCourseDto updateCourseDto);
    
    CourseDto toDto(Course course);
    
    List<CourseDto> toDtoList(List<Course> courses);
}
