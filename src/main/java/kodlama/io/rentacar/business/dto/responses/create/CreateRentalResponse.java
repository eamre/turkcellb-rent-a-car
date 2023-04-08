package kodlama.io.rentacar.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateRentalResponse {
    private int id;
    private double dailyPrice;
    private int rentedForDays;
    private double totalPrice;
    private LocalDateTime startDate;
    private int carId;
}
