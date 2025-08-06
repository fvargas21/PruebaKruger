package sasf.net.kevaluacion.dto.session;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sasf.net.kevaluacion.util.Constants;
import sasf.net.kevaluacion.validation.ValidationGroups;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionDto {
    
    @NotNull(message = Constants.SESSION_SCHEDULED_DATE_REQUIRED, groups = ValidationGroups.Create.class)
    @Future(message = Constants.SESSION_SCHEDULED_DATE_FUTURE, groups = ValidationGroups.Create.class)
    private LocalDate scheduledDate;
    
    @NotNull(message = Constants.SESSION_CAPACITY_REQUIRED, groups = ValidationGroups.Create.class)
    @Positive(message = Constants.SESSION_CAPACITY_POSITIVE, groups = ValidationGroups.Create.class)
    private Integer capacity;
}
