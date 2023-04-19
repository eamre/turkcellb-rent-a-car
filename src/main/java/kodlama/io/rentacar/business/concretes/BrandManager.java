package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.BrandService;
import kodlama.io.rentacar.business.dto.requests.create.CreateBrandRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateBrandRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateBrandResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllBrandsResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetBrandResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateBrandResponse;
import kodlama.io.rentacar.business.rules.BrandBusinessRules;
import kodlama.io.rentacar.entities.concretes.Brand;
import kodlama.io.rentacar.repository.abstracts.BrandRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BrandManager implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper mapper;
    private final BrandBusinessRules rules;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();
        List<GetAllBrandsResponse> responses = brands.stream()
                .map(brand ->mapper.map(brand,GetAllBrandsResponse.class)).toList();
        return responses;

       // return brandRepository.findAll();
    }

    @Override
    public GetBrandResponse getById(int id) {
        rules.checkIfBrandExists(id);
        Brand brand = brandRepository.findById(id).orElseThrow();
        GetBrandResponse response = mapper.map(brand,GetBrandResponse.class);

        return response;
        //return brandRepository.findById(id).orElseThrow();
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        rules.checkIfBrandExistsByName(request.getName());
        Brand brand = mapper.map(request,Brand.class);
        brand.setId(0);
        Brand createBrand = brandRepository.save(brand);

        CreateBrandResponse response =mapper.map(createBrand,CreateBrandResponse.class);
        return response;
//        Brand brand= new Brand();
//        brand.setName(request.getName());
//        brandRepository.save(brand);
//
//        CreateBrandResponse response = new CreateBrandResponse();
//        response.setId(brand.getId());
//        response.setName(brand.getName());

//        return response;
    }

    @Override
    public UpdateBrandResponse update(int id, UpdateBrandRequest brandRequest) {
        Brand brand = mapper.map(brandRequest,Brand.class);
        brand.setId(id);
        brandRepository.save(brand);

        UpdateBrandResponse response = mapper.map(brand,UpdateBrandResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }

    //business rules


}
//    BrandRepository brandRepository;
//
//    @Autowired
//    public BrandManager(BrandRepository brandRepository) {
//        this.brandRepository = brandRepository;
//    }
//
//    @Override
//    public List<Brand> getAll() {
//        if(brandRepository.getAll().size()==0){
//            System.out.println("kayıtlı marka yok");
//            return null;
//        }
//        return brandRepository.getAll();
//    }
//
//    @Override
//    public Brand getById(int id) {
//        return brandRepository.getById(id);
//    }
//
//    @Override
//    public Brand add(Brand product) {
//        return brandRepository.add(product);
//    }
//
//    @Override
//    public void delete(int id) {
//        brandRepository.delete(id);
//    }
//
//    @Override
//    public Brand update(int id, Brand product) {
//        return brandRepository.update(id,product);
//    }