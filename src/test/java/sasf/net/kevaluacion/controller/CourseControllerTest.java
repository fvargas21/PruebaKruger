package sasf.net.kevaluacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sasf.net.kevaluacion.dto.course.CreateCourseDto;
import sasf.net.kevaluacion.dto.course.CourseDto;
import sasf.net.kevaluacion.dto.course.UpdateCourseDto;
import sasf.net.kevaluacion.dto.session.CreateSessionDto;
import sasf.net.kevaluacion.dto.session.SessionDto;
import sasf.net.kevaluacion.dto.session.UpdateSessionDto;
import sasf.net.kevaluacion.exception.ResourceNotFoundException;
import sasf.net.kevaluacion.service.CourseService;
import sasf.net.kevaluacion.service.SessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@DisplayName("Course Controller Tests")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseDto courseDto;
    private CreateCourseDto createCourseDto;
    private UpdateCourseDto updateCourseDto;
    private SessionDto sessionDto;
    private CreateSessionDto createSessionDto;
    private UpdateSessionDto updateSessionDto;

    @BeforeEach
    void setUp() {
        courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Spring Boot Fundamentals");
        courseDto.setDescription("Learn the basics of Spring Boot framework");
        courseDto.setMaxCapacity(20);
        courseDto.setCreatedAt(LocalDateTime.now());

        createCourseDto = new CreateCourseDto();
        createCourseDto.setName("Spring Boot Fundamentals");
        createCourseDto.setDescription("Learn the basics of Spring Boot framework");
        createCourseDto.setMaxCapacity(20);

        updateCourseDto = new UpdateCourseDto();
        updateCourseDto.setName("Spring Boot Advanced");
        updateCourseDto.setDescription("Advanced Spring Boot concepts");
        updateCourseDto.setMaxCapacity(15);

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setScheduledDate(LocalDate.now().plusDays(7));
        sessionDto.setCapacity(15);
        sessionDto.setEnrolledCount(0);

        createSessionDto = new CreateSessionDto();
        createSessionDto.setScheduledDate(LocalDate.now().plusDays(7));
        createSessionDto.setCapacity(15);

        updateSessionDto = new UpdateSessionDto();
        updateSessionDto.setScheduledDate(LocalDate.now().plusDays(14));
        updateSessionDto.setCapacity(18);
    }

    @Test
    @DisplayName("Should create course successfully")
    void createCourse_Success() throws Exception {
        // Given
        when(courseService.createCourse(any(CreateCourseDto.class)))
                .thenReturn(courseDto);

        // When & Then
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCourseDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot Fundamentals")))
                .andExpect(jsonPath("$.description", is("Learn the basics of Spring Boot framework")))
                .andExpect(jsonPath("$.maxCapacity", is(20)));
    }

    @Test
    @DisplayName("Should return validation error when creating course with invalid data")
    void createCourse_ValidationError() throws Exception {
        // Given
        CreateCourseDto invalidDto = new CreateCourseDto();
        invalidDto.setName(""); // Invalid empty name
        invalidDto.setDescription("");
        invalidDto.setMaxCapacity(0); // Invalid capacity

        // When & Then
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get all courses successfully")
    void getAllCourses_Success() throws Exception {
        // Given
        CourseDto course2 = new CourseDto();
        course2.setId(2L);
        course2.setName("Microservices Architecture");
        course2.setDescription("Design and implement microservices");
        course2.setMaxCapacity(15);

        List<CourseDto> courses = Arrays.asList(courseDto, course2);
        when(courseService.getAllCoursesWithSessions()).thenReturn(courses);

        // When & Then
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Spring Boot Fundamentals")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Microservices Architecture")));
    }

    @Test
    @DisplayName("Should get course by id successfully")
    void getCourseById_Success() throws Exception {
        // Given
        when(courseService.getCourseById(1L)).thenReturn(courseDto);

        // When & Then
        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot Fundamentals")))
                .andExpect(jsonPath("$.description", is("Learn the basics of Spring Boot framework")));
    }

    @Test
    @DisplayName("Should return 404 when course not found")
    void getCourseById_NotFound() throws Exception {
        // Given
        when(courseService.getCourseById(999L))
                .thenThrow(new ResourceNotFoundException("COURSE_NOT_FOUND", "Course not found"));

        // When & Then
        mockMvc.perform(get("/courses/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update course successfully")
    void updateCourse_Success() throws Exception {
        // Given
        CourseDto updatedCourse = new CourseDto();
        updatedCourse.setId(1L);
        updatedCourse.setName("Spring Boot Advanced");
        updatedCourse.setDescription("Advanced Spring Boot concepts");
        updatedCourse.setMaxCapacity(15);

        when(courseService.updateCourse(eq(1L), any(UpdateCourseDto.class)))
                .thenReturn(updatedCourse);

        // When & Then
        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCourseDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot Advanced")))
                .andExpect(jsonPath("$.description", is("Advanced Spring Boot concepts")))
                .andExpect(jsonPath("$.maxCapacity", is(15)));
    }

    @Test
    @DisplayName("Should create session for course successfully")
    void createSessionForCourse_Success() throws Exception {
        // Given
        when(sessionService.createSession(eq(1L), any(CreateSessionDto.class)))
                .thenReturn(sessionDto);

        // When & Then
        mockMvc.perform(post("/courses/1/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSessionDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.capacity", is(15)))
                .andExpect(jsonPath("$.enrolledCount", is(0)));
    }

    @Test
    @DisplayName("Should get sessions for course successfully")
    void getSessionsForCourse_Success() throws Exception {
        // Given
        SessionDto session2 = new SessionDto();
        session2.setId(2L);
        session2.setScheduledDate(LocalDate.now().plusDays(14));
        session2.setCapacity(20);
        session2.setEnrolledCount(5);

        List<SessionDto> sessions = Arrays.asList(sessionDto, session2);
        when(sessionService.getSessionsByCourseId(1L)).thenReturn(sessions);

        // When & Then
        mockMvc.perform(get("/courses/1/sessions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].capacity", is(15)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].enrolledCount", is(5)));
    }

    @Test
    @DisplayName("Should update session successfully")
    void updateSession_Success() throws Exception {
        // Given
        SessionDto updatedSession = new SessionDto();
        updatedSession.setId(1L);
        updatedSession.setScheduledDate(LocalDate.now().plusDays(14));
        updatedSession.setCapacity(18);
        updatedSession.setEnrolledCount(0);

        when(sessionService.updateSession(eq(1L), any(UpdateSessionDto.class)))
                .thenReturn(updatedSession);

        // When & Then
        mockMvc.perform(put("/courses/1/sessions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSessionDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.capacity", is(18)));
    }

    @Test
    @DisplayName("Should return validation error when creating session with invalid data")
    void createSession_ValidationError() throws Exception {
        // Given
        CreateSessionDto invalidDto = new CreateSessionDto();
        invalidDto.setScheduledDate(LocalDate.now().minusDays(1)); // Past date
        invalidDto.setCapacity(0); // Invalid capacity

        // When & Then
        mockMvc.perform(post("/courses/1/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent session")
    void updateSession_NotFound() throws Exception {
        // Given
        when(sessionService.updateSession(eq(999L), any(UpdateSessionDto.class)))
                .thenThrow(new ResourceNotFoundException("SESSION_NOT_FOUND", "Session not found"));

        // When & Then
        mockMvc.perform(put("/courses/1/sessions/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSessionDto)))
                .andExpect(status().isNotFound());
    }
}
