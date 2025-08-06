package sasf.net.kevaluacion.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    
    private Long id;
    private String fullName;
    private String email;
}
