package kodlama.io.rentacar.business.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMaintenanceResponse {
    private int id;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private boolean isCompleted;
    private double maintenanceCost;
    private String description;
    private int carId;
}
