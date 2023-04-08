package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.business.dto.requests.create.CreateCarRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateCarRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateCarResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllCarsResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateCarResponse;
import kodlama.io.rentacar.entities.concretes.Car;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.abstracts.CarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CarManager implements CarService {
    private CarRepository carRepository;
    private ModelMapper mapper;

    @Override
    public List<GetAllCarsResponse> getAll(boolean showMaintance) {
        List<Car> cars = carRepository.findAll();
        List<GetAllCarsResponse> responses = cars
                .stream()
                .filter(car -> showMaintance || !car.getState().equals(State.MAINTENANCE))
                .map(car -> mapper.map(car, GetAllCarsResponse.class)).toList();

        return responses;
    }

    @Override
    public GetCarResponse getById(int id) {
        Car car = carRepository.findById(id).orElseThrow();
        GetCarResponse response = mapper.map(car,GetCarResponse.class);

        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        Car car = mapper.map(request,Car.class);
        car.setId(0);
        car.setState(State.AVAILABLE);

        carRepository.save(car);

        CreateCarResponse response = mapper.map(car,CreateCarResponse.class);
        return response;
    }

    @Override
    public UpdateCarResponse update(int id, UpdateCarRequest request) {
        checkIfExistsById(id);
        Car car = mapper.map(request,Car.class);
        car.setId(id);
        carRepository.save(car);

        UpdateCarResponse response = mapper.map(car,UpdateCarResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        carRepository.deleteById(id);
    }

    @Override
    public void changeState(int id, State state) {
       Car car = carRepository.findById(id).orElseThrow();
       car.setState(state);
       carRepository.save(car);
    }



//    public void sendCarToMaintenance(int id) {
//        Car car = carRepository.findById(id).orElseThrow();
//        checkIfCarStateRented(car.getState());
//        checkIfCarStateMaintance(car.getState());
//        car.setState(State.MAINTANCE);
//        car.setId(id);
//        carRepository.save(car);
//    }
//
//    public void carAvailable(int id) {
//        Car car = carRepository.findById(id).orElseThrow();
//        //checkIfCarStateRented(car.getState());
//        checkIfCarStateAvailable(car.getState());
//        car.setState(State.AVAILABLE);
//        car.setId(id);
//        carRepository.save(car);
//    }


    private void checkIfExistsById(int id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Böyle bir araç bulunamadı!");
        }
    }

    private void checkIfCarStateRented(State state) {
        if (state.equals(State.RENTED)) {
            throw new RuntimeException("Araç kirada bakıma gönderilemez.");
        }
    }

    private void checkIfCarStateMaintance(State state) {
        if (state.equals(State.MAINTENANCE)) {
            throw new RuntimeException("bakımda olan araba bakıma gönderilemez");
        }
    }

    private void checkIfCarStateAvailable(State state) {
        if (state.equals(State.AVAILABLE)) {
            throw new RuntimeException("Araba kiralanabilir durumda.");
        }
    }
}
