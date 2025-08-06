package sasf.net.kevaluacion.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeDto {
    
    @NotBlank(message = Constants.EMPLOYEE_FULL_NAME_REQUIRED, groups = ValidationGroups.Update.class)
    private String fullName;
    
    @NotBlank(message = Constants.EMPLOYEE_EMAIL_REQUIRED, groups = ValidationGroups.Update.class)
    @Email(message = Constants.EMPLOYEE_EMAIL_INVALID, groups = ValidationGroups.Update.class)
    private String email;
}
