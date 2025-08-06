package sasf.net.kevaluacion.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
    
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private Long sessionId;
    private String courseName;
    private LocalDate sessionDate;
    private Double grade;
}
