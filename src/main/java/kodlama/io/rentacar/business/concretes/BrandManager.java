package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.BrandService;
import kodlama.io.rentacar.entities.concretes.Brand;
import kodlama.io.rentacar.repository.abstracts.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BrandManager implements BrandService {

    private final BrandRepository brandRepository;
    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getById(int id) {
        checkIfBrandExists(id);
        return brandRepository.findById(id).orElseThrow();
    }

    @Override
    public Brand add(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand update(int id, Brand brand) {
        brand.setId(id);
        return brandRepository.save(brand);
    }

    private void checkIfBrandExists(int id){
        if (!brandRepository.existsById(id)) throw new RuntimeException("Böyle bir marka mevcut değil.");
    }

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