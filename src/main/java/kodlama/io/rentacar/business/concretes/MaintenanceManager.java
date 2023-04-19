package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.business.abstracts.MaintenanceService;
import kodlama.io.rentacar.business.dto.requests.create.CreateMaintenanceRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateMaintenanceRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateMaintenanceResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllMaintenancesResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetMaintenanceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateMaintenanceResponse;
import kodlama.io.rentacar.business.rules.MaintenanceBusinessRules;
import kodlama.io.rentacar.entities.concretes.Maintenance;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.abstracts.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class MaintenanceManager implements MaintenanceService {

    private MaintenanceRepository repository;
    private CarService carService;
    private ModelMapper mapper;
    private MaintenanceBusinessRules rules;

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
        rules.checkIfMaintenanceExists(id);
        Maintenance maintenance = repository.findById(id).orElseThrow();
        GetMaintenanceResponse response = mapper.map(maintenance,GetMaintenanceResponse.class);
        return response;
    }

    public GetMaintenanceResponse returnCarFromMaintenance(int carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId);
        Maintenance maintenance = repository.findMaintenanceByCarIdAndIsCompletedFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setDueDate(LocalDateTime.now());
        repository.save(maintenance);

        carService.changeState(carId,State.AVAILABLE);

        GetMaintenanceResponse response = mapper.map(maintenance,GetMaintenanceResponse.class);
        return response;
    }
    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkCarAvailabilityForMaintenance((carService.getById(request.getCarId()).getState()));
        rules.checkIfCarUnderMaintenance(request.getCarId());
        Maintenance maintenance = mapper.map(request,Maintenance.class);
        maintenance.setId(0);
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setDueDate(null);

        Maintenance createMaintenance = repository.save(maintenance);
        carService.changeState(request.getCarId(), State.MAINTENANCE);

        CreateMaintenanceResponse response = mapper.map(createMaintenance,CreateMaintenanceResponse.class);
        return response;

    }

    @Override
    public UpdateMaintenanceResponse update(int id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id);
        Maintenance maintenance = mapper.map(request,Maintenance.class);
        maintenance.setId(id);

        Maintenance updateMaintenance = repository.save(maintenance);

        UpdateMaintenanceResponse response = mapper.map(updateMaintenance,UpdateMaintenanceResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        rules.checkIfMaintenanceExists(id);
        makeCarAvailableIfIsCompletedFalse(id);
        repository.deleteById(id);
    }


    //business rules

    private void makeCarAvailableIfIsCompletedFalse(int id){
        int carId = repository.findById(id).get().getCar().getId();
        if(repository.existsByCarIdAndIsCompletedIsFalse(carId)){
            carService.changeState(carId, State.AVAILABLE);
        }
    }
}
//    private void changeCarStateToMaintenance(int carId){
//        GetCarResponse carResponse = carService.getById(carId);
//        carResponse.setState(State.MAINTENANCE);
//
//        UpdateCarRequest updateCarRequest = mapper.map(carResponse, UpdateCarRequest.class);
//        carService.update(carResponse.getId(),updateCarRequest);
//    }

//    private void changeCarStateToAvailable(int carId) {
//        GetCarResponse carResponse = carService.getById(carId);
//        carResponse.setState(State.AVAILABLE);
//
//        UpdateCarRequest updateCarRequest = mapper.map(carResponse, UpdateCarRequest.class);
//        carService.update(carId, updateCarRequest);
//    }
//    private void checkCompletedMaintenance(UpdateMaintenanceRequest request){
//        GetCarResponse carResponse = carService.getById(request.getCarId());
//
//        if(request.getDueDate()!=null && request.isCompletedMaintenance()){
//            carResponse.setState(State.AVAILABLE);
//        }
//        else if(request.getDueDate()==null && request.isCompletedMaintenance())
//            throw new RuntimeException("bitiş tarihini yazınız.");
//
//        UpdateCarRequest updateCarRequest = mapper.map(carResponse, UpdateCarRequest.class);
//        carService.update(carResponse.getId(),updateCarRequest);
//    }

//    private void checkIfCarCanBeSendToMaintenance(int carId){
//        checkIfCarStateMaintenance(carId);
//        checkIfCarStateRented(carId);
//    }


//    private void checkIfCarStateRented(int carId) {
//        GetCarResponse car = carService.getById(carId);
//        if (car.getState()==State.RENTED) {
//            throw new RuntimeException("Araç kirada bakıma gönderilemez.");
//        }
//    }

//    private void checkIfCarStateMaintenance(int carId) {
//        GetCarResponse car = carService.getById(carId);
//        if (car.getState()==(State.MAINTENANCE)) {
//            throw new RuntimeException("bakımda olan araba bakıma gönderilemez");
//        }
//    }

//    private void checkIfCarStateAvailable(int carId) {
//        GetCarResponse car = carService.getById(carId);
//        if (car.getState()==(State.AVAILABLE)) {
//            throw new RuntimeException("Araba kiralanabilir durumda.");
//        }
//    }


//checkIfCarCanBeSendToMaintenance(car.getId());

//    private void checkDueDate(Maintenance updateMaintenance){
////        if (updateMaintenance.getDueDate()!=null && updateMaintenance.getDueDate().before(new Date())) {
////            changeCarStateToAvailable(updateMaintenance.getCar().getId());
////        }
////    }

//    arabalar bakıma (maintenance) gönderilebilmelidir. Bakımdan gelen araba yeniden kiralanabilir duruma gelmelidir.
//    Zaten bakımda olan araba bakıma gönderilememez.
//    Kirada olan araba bakıma gönderilemez.
//    Bakımda olan araba araba listesinde görüntülenip görüntülenmeyeceğine kullanıcıdan bir parametre alarak gelmelidir veya gelmemelidir.