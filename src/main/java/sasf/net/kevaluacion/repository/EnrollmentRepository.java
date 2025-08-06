package sasf.net.kevaluacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sasf.net.kevaluacion.entity.Enrollment;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    List<Enrollment> findByEmployeeId(Long employeeId);
    
    List<Enrollment> findBySessionId(Long sessionId);
    
    Optional<Enrollment> findByEmployeeIdAndSessionId(Long employeeId, Long sessionId);
    
    boolean existsByEmployeeIdAndSessionId(Long employeeId, Long sessionId);
    
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.session.id = :sessionId")
    Long countBySessionId(@Param("sessionId") Long sessionId);
}
