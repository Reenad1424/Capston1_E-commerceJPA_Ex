package org.example.ecommercejpa_ex.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.example.ecommercejpa_ex.Api.ApiResponse;
import org.example.ecommercejpa_ex.Model.Product;
import org.example.ecommercejpa_ex.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(productService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        int result = productService.add(product);

        if (result == 1)
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category ID not found"));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (productService.update(id, product))
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product or Category not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (productService.delete(id))
            return ResponseEntity.status(200).body(new ApiResponse("Product Deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

    // Get Products By Category Name
    @GetMapping("/get-product-ByCategory/{categoryName}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String categoryName) {
        List<Product> products = productService.getByCategoryName(categoryName);

        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("Not found products in this category"));
        }

        return ResponseEntity.status(200).body(products);
    }

    // Endpoint for cheapest products
    @GetMapping("/cheapest/{categoryName}")
    public ResponseEntity<?> getCheapest(@PathVariable String categoryName) {
        List<Product> list = productService.getCheapestProducts(categoryName);
        if (list == null || list.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No products found"));
        return ResponseEntity.status(200).body(list);
    }

    // Endpoint for most expensive products
    @GetMapping("/expensive/{categoryName}")
    public ResponseEntity<?> getExpensive(@PathVariable String categoryName) {
        List<Product> list = productService.getMostExpensiveProducts(categoryName);
        if (list == null || list.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No products found"));
        return ResponseEntity.status(200).body(list);
    }
}

