package com.jaiveer.backend.product;

import com.jaiveer.backend.user.User;
import com.jaiveer.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/product")
    ResponseEntity<Product> newProduct(@RequestBody Product Product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Product));
    }

    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> all() {
        List<EntityModel<Product>> Products = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(CollectionModel.of(Products, linkTo(methodOn(ProductController.class).all()).withSelfRel()));
    }


    @PostMapping("/products")
    ResponseEntity<List<Product>> newProduct(@RequestBody List<Product> Products) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.saveAll(Products));
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<EntityModel<Product>> getById(@PathVariable Long id) {
        Product Product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ResponseEntity.ok(assembler.toModel(Product));
    }


    @PutMapping("products/{id}")
    ResponseEntity<Product> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return repository.findById(id).map(Product -> {
            Product.setProductName(newProduct.getProductName());
            Product.setCategory(newProduct.getCategory());
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Product));
        }).orElseGet(() -> {
            newProduct.setId(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newProduct));
        });
    }


    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }


    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = repository.findUniqueCategories();

        if (categories == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(repository.findUniqueCategories());
    }


    @GetMapping("/categories/{cat}")
    public ResponseEntity<List<Product>> getById(@PathVariable String cat) {
        return ResponseEntity.ok(repository.findAllByCategory(cat));
    }

    //temp for testing
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }
}
