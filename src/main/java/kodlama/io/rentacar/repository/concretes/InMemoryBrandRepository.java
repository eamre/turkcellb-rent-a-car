package kodlama.io.rentacar.repository.concretes;

import kodlama.io.rentacar.entities.concretes.Brand;
import kodlama.io.rentacar.repository.abstracts.BrandRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryBrandRepository implements BrandRepository {
    List<Brand> brands;

    public InMemoryBrandRepository() {
        brands= new ArrayList<>();
        brands.add(new Brand(1,"mercedes"));
        brands.add(new Brand(2,"bmw"));
        brands.add(new Brand(3,"audi"));
        brands.add(new Brand(4,"volvo"));
        brands.add(new Brand(5,"tofaş"));

    }

    @Override
    public List<Brand> getAll() {
        return brands;
    }
    @Override
    public Brand getById(int id) {
        for (Brand brand : brands) {
            if (brand.getId()==id){
                return brand;
            }
        }
        return null;
    }

    @Override
    public Brand add(Brand brand) {
        brands.add(brand);
        return brand;
    }

    @Override
    public void delete(int id) {
        brands.removeIf(b -> b.getId()==id);
    }

    @Override
    public Brand update(int id,Brand product) {
        for (Brand br : brands) {
            if (br.getId()==id){
                br.setName(product.getName());

                return br;
            }
        }
        return null;
    }
}
