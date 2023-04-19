package kodlama.io.rentacar.repository.abstracts;

import kodlama.io.rentacar.entities.concretes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

//repoya gerek yok
public interface BrandRepository extends JpaRepository<Brand,Integer> {
// custom query
    boolean existsByNameIgnoreCase(String name);
}
//    List<Brand> getAll();
//    Brand getById(int id);
//    Brand add(Brand product);
//    void delete(int id);
//    Brand update(int id,Brand product);