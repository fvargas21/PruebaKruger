package sasf.net.kevaluacion.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    
    private Long id;
    private Long courseId;
    private String courseName;
    private LocalDate scheduledDate;
    private Integer capacity;
    private Integer enrolledCount;
}
