package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.ModelService;
import kodlama.io.rentacar.business.dto.requests.create.CreateModelRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateModelRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateModelResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllModelsResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetModelResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateModelResponse;
import kodlama.io.rentacar.business.rules.ModelBusinessRules;
import kodlama.io.rentacar.entities.concretes.Model;
import kodlama.io.rentacar.repository.abstracts.ModelRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
    private ModelRepository modelRepository;
    private ModelMapper mapper;
    private final ModelBusinessRules rules;

    @Override
    public List<GetAllModelsResponse> getAll() {
        List<Model> models= modelRepository.findAll();
        List<GetAllModelsResponse> responses=
                models.stream().map(model -> mapper.map(model,GetAllModelsResponse.class)).toList();
        return responses;
    }

    @Override
    public GetModelResponse getById(int id) {
        rules.checkIfModelExists(id);
        Model model = modelRepository.findById(id).orElseThrow();
        GetModelResponse response = mapper.map(model,GetModelResponse.class);
        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        Model model = mapper.map(request,Model.class);
        model.setId(0);
        modelRepository.save(model);

        CreateModelResponse response = mapper.map(model,CreateModelResponse.class);
        return response;
    }

    @Override
    public UpdateModelResponse update(int id, UpdateModelRequest request) {
        rules.checkIfModelExists(id);
        Model model = mapper.map(request,Model.class);
        model.setId(id);
        modelRepository.save(model);

        UpdateModelResponse response = mapper.map(model,UpdateModelResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        rules.checkIfModelExists(id);
        modelRepository.deleteById(id);
    }
}
