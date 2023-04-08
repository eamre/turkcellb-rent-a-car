package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.business.abstracts.RentalService;
import kodlama.io.rentacar.business.dto.requests.create.CreateRentalRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateRentalRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateRentalResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllRentalResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetRentalResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateRentalResponse;
import kodlama.io.rentacar.entities.concretes.Rental;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.abstracts.RentalRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor

public class RentalManager implements RentalService {
    private RentalRepository repository;
    private ModelMapper mapper;
    private CarService carService;
    @Override
    public List<GetAllRentalResponse> getAll() {
        List<Rental> rentals = repository.findAll();
        List<GetAllRentalResponse> responses = rentals
                .stream()
                .map(rental -> mapper.map(rental,GetAllRentalResponse.class)).toList();

        return responses;
    }

    @Override
    public GetRentalResponse getById(int id) {
        checkIfRentalExists(id);
        Rental rental = repository.findById(id).orElseThrow();
        GetRentalResponse response = mapper.map(rental,GetRentalResponse.class);
        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        checkIfCarAvailable(request.getCarId());
        Rental rental = mapper.map(request,Rental.class);
        rental.setId(0);
        rental.setStartDate(LocalDateTime.now());
        rental.setTotalPrice(getTotalPrice(rental));
        repository.save(rental);

        carService.changeState(rental.getCar().getId(), State.RENTED);

        CreateRentalResponse response = mapper.map(rental,CreateRentalResponse.class) ;
        return response;
    }

    @Override
    public UpdateRentalResponse update(int id, UpdateRentalRequest request) {
        checkIfRentalExists(id);
        Rental rental = mapper.map(request,Rental.class);
        rental.setId(id);
        rental.setTotalPrice(getTotalPrice(rental));
        repository.save(rental);

        UpdateRentalResponse response = mapper.map(rental,UpdateRentalResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfRentalExists(id);
        int carId = repository.findById(id).get().getCar().getId();
        repository.deleteById(id);
        carService.changeState(carId,State.AVAILABLE);
    }


    private double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }

    private void checkIfRentalExists(int id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Kiralama bilgisine ulaşılamadı!");
        }
    }

    private void checkIfCarAvailable(int carId) {
        if(!carService.getById(carId).getState().equals(State.AVAILABLE)){
            throw new RuntimeException("Araç müsait değil!");
        }
    }

}