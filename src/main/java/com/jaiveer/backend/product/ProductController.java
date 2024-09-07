package com.jaiveer.backend.product;

import com.jaiveer.backend.user.User;
import com.jaiveer.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final ProductRepository productRepository;
    private final ProductModelAssembler productModelAssembler;
    //temp for testing
    private final UserRepository userRepository;
    private final ProductService productService;


    @PostMapping("/product")
    ResponseEntity<Product> addProduct(@RequestBody Product Product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(Product));
    }

    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> retrieveAllProducts() {
        List<EntityModel<Product>> Products = productRepository.findAll().stream()
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(CollectionModel.of(Products, linkTo(methodOn(ProductController.class).retrieveAllProducts()).withSelfRel()));
    }


    @PostMapping("/products")
    ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> Products) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.saveAll(Products));
    }

    @GetMapping("/products/get")
    public ResponseEntity<List<Product>> getProductNamesStartingWith(@RequestParam String search) {
        return ResponseEntity.ok(productRepository.findByProductNameContaining(search, Pageable.ofSize(5)).getContent());
    }

    @GetMapping("/products/recommended")
    public ResponseEntity<List<Product>> generateRecommendedProducts(@RequestParam String id) {
        Long productId = Long.parseLong(id);
        List<Product> recommended = productService.createRecommended(productId, 4);
        return ResponseEntity.ok(recommended);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable Long id) {
        Product Product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ResponseEntity.ok(productModelAssembler.toModel(Product));
    }


    @PutMapping("products/{id}")
    ResponseEntity<Product> replaceProductById(@RequestBody Product updatedProduct, @PathVariable Long id) {
        return productRepository.findById(id).map(Product -> {
            Product.setProductName(updatedProduct.getProductName());
            Product.setCategory(updatedProduct.getCategory());
            return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(Product));
        }).orElseGet(() -> {
            updatedProduct.setId(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(updatedProduct));
        });
    }


    @DeleteMapping("/products/{id}")
    void deleteProductById(@PathVariable Long id) {
        productRepository.deleteById(id);
    }


    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = productRepository.findUniqueCategories();

        if (categories == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productRepository.findUniqueCategories());
    }


    @GetMapping("/categories/{cat}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String cat) {
        return ResponseEntity.ok(productRepository.findAllByCategory(cat));
    }

    //temp for testing
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
