package kodlama.io.rentacar.business.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaintenanceRequest {
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private boolean isCompleted;
    private double maintenanceCost;
    private String description;
    private boolean completedMaintenance;
    private int carId;
}
