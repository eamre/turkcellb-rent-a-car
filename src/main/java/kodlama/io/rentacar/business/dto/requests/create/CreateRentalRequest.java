package kodlama.io.rentacar.business.dto.requests.create;

import kodlama.io.rentacar.business.dto.requests.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateRentalRequest {
    private int carId;
    private double dailyPrice;
    private int rentedForDays;

    //Kart bilgileri
    private PaymentRequest paymentRequest;


}

