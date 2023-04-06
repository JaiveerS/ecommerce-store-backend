package com.jaiveer.backend.product;

import com.jaiveer.backend.user.User;
import com.jaiveer.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductRepository repository;
    private final ProductModelAssembler assembler;
    //temp for testing
    private final UserRepository userRepo;

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all() {
        List<EntityModel<Product>> Products = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(Products, linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    @PostMapping("/product")
    Product newProduct(@RequestBody Product Product) {
        return repository.save(Product);
    }

    @PostMapping("products")
    List<Product> newProduct(@RequestBody List<Product> Products) {
        return repository.saveAll(Products);
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> getById(@PathVariable Long id) {
        Product Product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toModel(Product);
    }

    @PutMapping("products/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return repository.findById(id).map(Product -> {
            Product.setProductName(newProduct.getProductName());
            Product.setCategory(newProduct.getCategory());
            return repository.save(Product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return repository.save(newProduct);
        });
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }


    //temp for testing
    @GetMapping("/test")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}
