package sasf.net.kevaluacion.dto.enrollment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnrollmentDto {
    
    @NotNull(message = Constants.ENROLLMENT_EMPLOYEE_ID_REQUIRED, groups = ValidationGroups.Create.class)
    private Long employeeId;
}
