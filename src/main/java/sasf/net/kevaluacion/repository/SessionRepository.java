package sasf.net.kevaluacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sasf.net.kevaluacion.entity.Session;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    List<Session> findByCourseId(Long courseId);
    
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.session.id = :sessionId")
    Long countEnrolledEmployeesBySessionId(@Param("sessionId") Long sessionId);
}
