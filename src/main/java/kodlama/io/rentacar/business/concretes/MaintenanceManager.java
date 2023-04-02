package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.business.abstracts.MaintenanceService;
import kodlama.io.rentacar.business.dto.requests.create.CreateMaintenanceRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateCarRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateMaintenanceRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateMaintenanceResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllMaintenancesResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetMaintenanceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateMaintenanceResponse;
import kodlama.io.rentacar.entities.concretes.Maintenance;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.abstracts.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MaintenanceManager implements MaintenanceService {

    private MaintenanceRepository repository;
    private CarService carService;
    private ModelMapper mapper;

    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        List<Maintenance> maintenances = repository.findAll();
        List<GetAllMaintenancesResponse> responses = maintenances
                .stream()
                .map(car -> mapper.map(car, GetAllMaintenancesResponse.class)).toList();
        return responses;
    }

    @Override
    public GetMaintenanceResponse getById(int id) {
        Maintenance maintenance = repository.findById(id).orElseThrow();
        GetMaintenanceResponse response = mapper.map(maintenance,GetMaintenanceResponse.class);
        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        Maintenance maintenance = mapper.map(request,Maintenance.class);
        maintenance.setId(0);

        checkIfCarCanBeSendToMaintenance(request.getCarId());
        Maintenance createMaintenance = repository.save(maintenance);
        changeCarState(request.getCarId());
        CreateMaintenanceResponse response = mapper.map(createMaintenance,CreateMaintenanceResponse.class);
        return response;

    }

    @Override
    public UpdateMaintenanceResponse update(int id, UpdateMaintenanceRequest request) {
        Maintenance maintenance = mapper.map(request,Maintenance.class);
        maintenance.setId(id);
        checkIfCarCanBeSendToMaintenance(request.getCarId());
        Maintenance updateMaintenance = repository.save(maintenance);

        UpdateMaintenanceResponse response = mapper.map(updateMaintenance,UpdateMaintenanceResponse.class);
        return response;
    }


    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }



    //checkIfCarCanBeSendToMaintenance(car.getId());
    private void changeCarState(int carId){
        GetCarResponse carResponse = carService.getById(carId);
        carResponse.setState(State.MAINTANCE);

        UpdateCarRequest updateCarRequest = mapper.map(carResponse, UpdateCarRequest.class);
        carService.update(carResponse.getId(),updateCarRequest);
    }


    private void checkIfCarCanBeSendToMaintenance(int carId){
        checkIfCarStateMaintenance(carId);
        checkIfCarStateRented(carId);
    }
    private void checkIfCarStateRented(int carId) {
        GetCarResponse car = carService.getById(carId);
        if (car.getState()==State.RENTED) {
            throw new RuntimeException("Araç kirada bakıma gönderilemez.");
        }
    }
    private void checkIfCarStateMaintenance(int carId) {
        GetCarResponse car = carService.getById(carId);
        if (car.getState()==(State.MAINTANCE)) {
            throw new RuntimeException("bakımda olan araba bakıma gönderilemez");
        }
    }
    private void checkIfCarStateAvailable(int carId) {
        GetCarResponse car = carService.getById(carId);
        if (car.getState()==(State.AVAILABLE)) {
            throw new RuntimeException("Araba kiralanabilir durumda.");
        }
    }
}
//    arabalar bakıma (maintenance) gönderilebilmelidir. Bakımdan gelen araba yeniden kiralanabilir duruma gelmelidir.
//    Zaten bakımda olan araba bakıma gönderilememez.
//    Kirada olan araba bakıma gönderilemez.
//    Bakımda olan araba araba listesinde görüntülenip görüntülenmeyeceğine kullanıcıdan bir parametre alarak gelmelidir veya gelmemelidir.