package kodlama.io.rentacar.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaintenanceResponse {
    private int id;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private boolean isCompleted;
    private double maintenanceCost;
    private String description;
    private int carId;
}
