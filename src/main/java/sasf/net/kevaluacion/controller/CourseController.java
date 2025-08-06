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
import sasf.net.kevaluacion.dto.course.CreateCourseDto;
import sasf.net.kevaluacion.dto.course.CourseDto;
import sasf.net.kevaluacion.dto.course.UpdateCourseDto;
import sasf.net.kevaluacion.dto.session.CreateSessionDto;
import sasf.net.kevaluacion.dto.session.SessionDto;
import sasf.net.kevaluacion.dto.session.UpdateSessionDto;
import sasf.net.kevaluacion.service.CourseService;
import sasf.net.kevaluacion.service.SessionService;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = Constants.COURSE_TAG_NAME, description = Constants.COURSE_TAG_DESCRIPTION)
public class CourseController {
    
    private final CourseService courseService;
    private final SessionService sessionService;
    
    @PostMapping
    @Operation(summary = Constants.COURSE_CREATE_SUMMARY, description = Constants.COURSE_CREATE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = Constants.RESPONSE_201_CREATED),
            @ApiResponse(responseCode = "400", description = Constants.RESPONSE_400_INVALID_INPUT)
    })
    public ResponseEntity<CourseDto> createCourse(
            @Validated(ValidationGroups.Create.class) @RequestBody CreateCourseDto createCourseDto) {
        CourseDto courseDto = courseService.createCourse(createCourseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDto);
    }
    
    @GetMapping
    @Operation(summary = Constants.COURSE_GET_ALL_SUMMARY, description = Constants.COURSE_GET_ALL_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_LIST_RETRIEVED)
    })
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCoursesWithSessions();
        return ResponseEntity.ok(courses);
    }
    
    @PostMapping("/{courseId}/sessions")
    @Operation(summary = Constants.SESSION_CREATE_SUMMARY, description = Constants.SESSION_CREATE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = Constants.RESPONSE_201_CREATED),
            @ApiResponse(responseCode = "400", description = Constants.CAPACITY_EXCEEDS_LIMIT),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<SessionDto> createSession(
            @Parameter(description = Constants.PARAM_COURSE_ID, required = true)
            @PathVariable Long courseId,
            @Validated(ValidationGroups.Create.class) @RequestBody CreateSessionDto createSessionDto) {
        SessionDto sessionDto = sessionService.createSession(courseId, createSessionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionDto);
    }
    
    @GetMapping("/{courseId}")
    @Operation(summary = Constants.COURSE_GET_BY_ID_SUMMARY, description = Constants.COURSE_GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_SUCCESS),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<CourseDto> getCourseById(
            @Parameter(description = Constants.PARAM_COURSE_ID, required = true)
            @PathVariable Long courseId) {
        CourseDto course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }
    
    @PutMapping("/{courseId}")
    @Operation(summary = Constants.COURSE_UPDATE_SUMMARY, description = Constants.COURSE_UPDATE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_SUCCESS),
            @ApiResponse(responseCode = "400", description = Constants.RESPONSE_400_INVALID_INPUT),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<CourseDto> updateCourse(
            @Parameter(description = Constants.PARAM_COURSE_ID, required = true)
            @PathVariable Long courseId,
            @Validated(ValidationGroups.Update.class) @RequestBody UpdateCourseDto updateCourseDto) {
        CourseDto courseDto = courseService.updateCourse(courseId, updateCourseDto);
        return ResponseEntity.ok(courseDto);
    }
    
    @GetMapping("/{courseId}/sessions")
    @Operation(summary = Constants.SESSION_GET_SESSIONS_SUMMARY, description = Constants.SESSION_GET_SESSIONS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_LIST_RETRIEVED),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<List<SessionDto>> getCourseSessions(
            @Parameter(description = Constants.PARAM_COURSE_ID, required = true)
            @PathVariable Long courseId) {
        List<SessionDto> sessions = sessionService.getSessionsByCourseId(courseId);
        return ResponseEntity.ok(sessions);
    }
    
    @PutMapping("/{courseId}/sessions/{sessionId}")
    @Operation(summary = Constants.SESSION_UPDATE_SUMMARY, description = Constants.SESSION_UPDATE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Constants.RESPONSE_200_SUCCESS),
            @ApiResponse(responseCode = "400", description = Constants.RESPONSE_400_INVALID_INPUT),
            @ApiResponse(responseCode = "404", description = Constants.RESPONSE_404_NOT_FOUND)
    })
    public ResponseEntity<SessionDto> updateSession(
            @Parameter(description = Constants.PARAM_COURSE_ID, required = true)
            @PathVariable Long courseId,
            @Parameter(description = Constants.PARAM_SESSION_ID, required = true)
            @PathVariable Long sessionId,
            @Validated(ValidationGroups.Update.class) @RequestBody UpdateSessionDto updateSessionDto) {
        SessionDto sessionDto = sessionService.updateSession(sessionId, updateSessionDto);
        return ResponseEntity.ok(sessionDto);
    }
}
