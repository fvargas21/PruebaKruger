package sasf.net.kevaluacion.dto.enrollment;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEnrollmentDto {
    
    @NotNull(message = Constants.ENROLLMENT_GRADE_REQUIRED, groups = ValidationGroups.Update.class)
    @DecimalMin(value = "0.0", message = Constants.ENROLLMENT_GRADE_RANGE, groups = ValidationGroups.Update.class)
    @DecimalMax(value = "10.0", message = Constants.ENROLLMENT_GRADE_RANGE, groups = ValidationGroups.Update.class)
    private Double grade;
}
