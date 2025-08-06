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
public class UpdateSessionDto {
    
    @NotNull(message = Constants.SESSION_SCHEDULED_DATE_REQUIRED, groups = ValidationGroups.Update.class)
    @Future(message = Constants.SESSION_SCHEDULED_DATE_FUTURE, groups = ValidationGroups.Update.class)
    private LocalDate scheduledDate;
    
    @NotNull(message = Constants.SESSION_CAPACITY_REQUIRED, groups = ValidationGroups.Update.class)
    @Positive(message = Constants.SESSION_CAPACITY_POSITIVE, groups = ValidationGroups.Update.class)
    private Integer capacity;
}
