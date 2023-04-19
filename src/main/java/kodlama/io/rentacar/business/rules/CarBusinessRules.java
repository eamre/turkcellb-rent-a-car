package kodlama.io.rentacar.business.rules;

import kodlama.io.rentacar.common.constants.Messages;
import kodlama.io.rentacar.core.exceptions.BusinessException;
import kodlama.io.rentacar.repository.abstracts.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarBusinessRules {
    private final CarRepository carRepository;

    public void checkIfExistsById(int id) {
        if (!carRepository.existsById(id)) {
            throw new BusinessException(Messages.Car.NotExists);
        }
    }
}
