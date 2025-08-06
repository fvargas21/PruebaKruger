package sasf.net.kevaluacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sasf.net.kevaluacion.dto.course.CreateCourseDto;
import sasf.net.kevaluacion.dto.course.CourseDto;
import sasf.net.kevaluacion.dto.course.UpdateCourseDto;
import sasf.net.kevaluacion.entity.Course;
import sasf.net.kevaluacion.exception.ResourceNotFoundException;
import sasf.net.kevaluacion.mapper.CourseMapper;
import sasf.net.kevaluacion.repository.CourseRepository;
import sasf.net.kevaluacion.service.CourseService;
import sasf.net.kevaluacion.util.Constants;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    public CourseDto createCourse(CreateCourseDto createCourseDto) {
        log.info("Creating course: {}", createCourseDto.getName());
        
        Course course = courseMapper.toEntity(createCourseDto);
        Course savedCourse = courseRepository.save(course);
        log.info("Course created successfully with id: {}", savedCourse.getId());
        return courseMapper.toDto(savedCourse);
    }
    
    public CourseDto updateCourse(Long courseId, UpdateCourseDto updateCourseDto) {
        log.info("Updating course with id: {}", courseId);
        
        Course course = findById(courseId);
        course.setName(updateCourseDto.getName());
        course.setDescription(updateCourseDto.getDescription());
        course.setMaxCapacity(updateCourseDto.getMaxCapacity());
        
        Course savedCourse = courseRepository.save(course);
        log.info("Course updated successfully with id: {}", savedCourse.getId());
        return courseMapper.toDto(savedCourse);
    }
    
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCoursesWithSessions() {
        log.info("Fetching all courses with sessions");
        List<Course> courses = courseRepository.findAllWithSessions();
        return courseMapper.toDtoList(courses);
    }
    
    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERROR_COURSE_NOT_FOUND, 
                        Constants.COURSE_NOT_FOUND + " ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        Course course = findById(id);
        return courseMapper.toDto(course);
    }
}
