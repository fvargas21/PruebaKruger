package sasf.net.kevaluacion.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sasf.net.kevaluacion.entity.Course;
import sasf.net.kevaluacion.entity.Employee;
import sasf.net.kevaluacion.entity.Enrollment;
import sasf.net.kevaluacion.entity.Session;
import sasf.net.kevaluacion.repository.CourseRepository;
import sasf.net.kevaluacion.repository.EmployeeRepository;
import sasf.net.kevaluacion.repository.EnrollmentRepository;
import sasf.net.kevaluacion.repository.SessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final EmployeeRepository employeeRepository;
    private final CourseRepository courseRepository;
    private final SessionRepository sessionRepository;
    private final EnrollmentRepository enrollmentRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() == 0) {
            loadSampleData();
        }
    }
    
    private void loadSampleData() {
        log.info("Loading sample data...");
        
        // Load data in order due to dependencies
        loadEmployees();
        loadCourses();
        loadSessions();
        loadEnrollments();
        
        log.info("Sample data loaded successfully!");
    }
    
    private void loadEmployees() {
        log.info("Creating sample employees...");
        
        Employee emp1 = new Employee();
        emp1.setFullName("Juan Pérez");
        emp1.setEmail("juan.perez@kevaluacion.com");
        
        Employee emp2 = new Employee();
        emp2.setFullName("María García");
        emp2.setEmail("maria.garcia@kevaluacion.com");
        
        Employee emp3 = new Employee();
        emp3.setFullName("Carlos López");
        emp3.setEmail("carlos.lopez@kevaluacion.com");
        
        employeeRepository.save(emp1);
        employeeRepository.save(emp2);
        employeeRepository.save(emp3);
        
        log.info("Created {} employees", 3);
    }
    
    private void loadCourses() {
        log.info("Creating sample courses...");
        
        Course course1 = new Course();
        course1.setName("Spring Boot Fundamentals");
        course1.setDescription("Learn the basics of Spring Boot framework");
        course1.setMaxCapacity(20);
        course1.setCreatedAt(LocalDateTime.now());
        
        Course course2 = new Course();
        course2.setName("Microservices Architecture");
        course2.setDescription("Design and implement microservices with Spring Cloud");
        course2.setMaxCapacity(15);
        course2.setCreatedAt(LocalDateTime.now());
        
        Course course3 = new Course();
        course3.setName("Database Design");
        course3.setDescription("Relational database design principles and best practices");
        course3.setMaxCapacity(25);
        course3.setCreatedAt(LocalDateTime.now());
        
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);
        
        log.info("Created {} courses", 3);
    }
    
    private void loadSessions() {
        log.info("Creating sample sessions...");
        
        // Get courses to associate with sessions
        Course course1 = courseRepository.findAll().get(0);
        Course course2 = courseRepository.findAll().get(1);
        Course course3 = courseRepository.findAll().get(2);
        
        Session session1 = new Session();
        session1.setCourse(course1);
        session1.setScheduledDate(LocalDate.now().plusDays(7));
        session1.setCapacity(15);
        
        Session session2 = new Session();
        session2.setCourse(course1);
        session2.setScheduledDate(LocalDate.now().plusDays(21));
        session2.setCapacity(20);
        
        Session session3 = new Session();
        session3.setCourse(course2);
        session3.setScheduledDate(LocalDate.now().plusDays(14));
        session3.setCapacity(10);
        
        Session session4 = new Session();
        session4.setCourse(course3);
        session4.setScheduledDate(LocalDate.now().plusDays(28));
        session4.setCapacity(25);
        
        sessionRepository.save(session1);
        sessionRepository.save(session2);
        sessionRepository.save(session3);
        sessionRepository.save(session4);
        
        log.info("Created {} sessions", 4);
    }
    
    private void loadEnrollments() {
        log.info("Creating sample enrollments...");
        
        // Get employees and sessions to create enrollments
        Employee emp1 = employeeRepository.findAll().get(0);
        Employee emp2 = employeeRepository.findAll().get(1);
        Employee emp3 = employeeRepository.findAll().get(2);
        
        Session session1 = sessionRepository.findAll().get(0);
        Session session2 = sessionRepository.findAll().get(1);
        Session session3 = sessionRepository.findAll().get(2);
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setEmployee(emp1);
        enrollment1.setSession(session1);
        enrollment1.setGrade(8.5);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setEmployee(emp2);
        enrollment2.setSession(session1);
        enrollment2.setGrade(9.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setEmployee(emp1);
        enrollment3.setSession(session3);
        // No grade yet
        
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setEmployee(emp3);
        enrollment4.setSession(session2);
        // No grade yet
        
        enrollmentRepository.save(enrollment1);
        enrollmentRepository.save(enrollment2);
        enrollmentRepository.save(enrollment3);
        enrollmentRepository.save(enrollment4);
        
        log.info("Created {} enrollments", 4);
    }
}
