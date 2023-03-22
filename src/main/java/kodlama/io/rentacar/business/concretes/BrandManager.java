package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.BrandService;
import kodlama.io.rentacar.entities.concretes.Brand;
import kodlama.io.rentacar.repository.abstracts.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandManager implements BrandService {
    BrandRepository brandRepository;

    @Autowired
    public BrandManager(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAll() {
        if(brandRepository.getAll().size()==0){
            System.out.println("kayıtlı marka yok");
            return null;
        }
        return brandRepository.getAll();
    }

    @Override
    public Brand getById(int id) {
        return brandRepository.getById(id);
    }

    @Override
    public Brand add(Brand product) {
        return brandRepository.add(product);
    }

    @Override
    public void delete(int id) {
        brandRepository.delete(id);
    }

    @Override
    public Brand update(int id, Brand product) {
        return brandRepository.update(id,product);
    }
}
