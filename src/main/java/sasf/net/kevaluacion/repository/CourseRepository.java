package sasf.net.kevaluacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sasf.net.kevaluacion.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.sessions")
    List<Course> findAllWithSessions();
}
