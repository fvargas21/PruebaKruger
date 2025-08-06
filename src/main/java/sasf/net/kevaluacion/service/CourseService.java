package sasf.net.kevaluacion.service;

import sasf.net.kevaluacion.dto.course.CreateCourseDto;
import sasf.net.kevaluacion.dto.course.CourseDto;
import sasf.net.kevaluacion.dto.course.UpdateCourseDto;
import sasf.net.kevaluacion.entity.Course;

import java.util.List;

public interface CourseService {
    
    CourseDto createCourse(CreateCourseDto createCourseDto);
    
    CourseDto updateCourse(Long courseId, UpdateCourseDto updateCourseDto);
    
    List<CourseDto> getAllCoursesWithSessions();
    
    Course findById(Long id);
    
    CourseDto getCourseById(Long id);
}
