package kodlama.io.rentacar.business.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateMaintenanceRequest {
    private Date startDate;
    private Date dueDate;
    private double maintenanceCost;
    private String description;
    private int carId;
}
