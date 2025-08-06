package sasf.net.kevaluacion.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDto {
    
    @NotBlank(message = Constants.COURSE_NAME_REQUIRED, groups = ValidationGroups.Create.class)
    private String name;
    
    private String description;
    
    @NotNull(message = Constants.COURSE_MAX_CAPACITY_REQUIRED, groups = ValidationGroups.Create.class)
    @Positive(message = Constants.COURSE_MAX_CAPACITY_POSITIVE, groups = ValidationGroups.Create.class)
    private Integer maxCapacity;
}
