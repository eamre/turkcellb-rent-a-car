package kodlama.io.rentacar.business.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateCarRequest {

    private int modelYear;
    private String plate;
    private double dailyPrice;
//    private State state;//bunu kullanmayabiliriz available olacak zaten update de olmalı
    private int modelId;
}
