package kodlama.io.rentacar.api.controllers;

import kodlama.io.rentacar.business.abstracts.BrandService;
import kodlama.io.rentacar.entities.concretes.Brand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/brands")
public class BrandsController {
    private BrandService brandService;

    public BrandsController(BrandService service) {
        this.brandService = service;
    }

    @GetMapping
    public List<Brand> findAll(){
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable int id){
        return brandService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Brand brand){
        brandService.add(brand);
    }

    @PutMapping ("/{id}")
    public void update(@PathVariable int id,@RequestBody Brand brand){
        brandService.update(id,brand);
    }

    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        brandService.delete(id);
    }
}
