package sasf.net.kevaluacion.dto.course;

import sasf.net.kevaluacion.dto.session.SessionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    
    private Long id;
    private String name;
    private String description;
    private Integer maxCapacity;
    private LocalDateTime createdAt;
    private List<SessionDto> sessions;
}
