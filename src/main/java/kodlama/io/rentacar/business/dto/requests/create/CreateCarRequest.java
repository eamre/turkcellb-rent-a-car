package kodlama.io.rentacar.business.dto.requests.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import kodlama.io.rentacar.common.utils.annotations.NotFutureYear;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateCarRequest {
    private int modelId;
    @Min(1998)
    @NotFutureYear
    private int modelYear;
    @Pattern(regexp = "")
    private String plate;
    @Min(1)
    private double dailyPrice;

//  private State state;//bunu kullanmayabiliriz available olacak zaten update de olmalı

}
