package org.example.ecommercejpa_ex.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Api.ApiResponse;
import org.example.ecommercejpa_ex.Model.Category;
import org.example.ecommercejpa_ex.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.status(200).body(categoryService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        categoryService.add(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        if(categoryService.update(id, category))
            return ResponseEntity.status(200).body(new ApiResponse("Category Updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if(categoryService.delete(id))
            return ResponseEntity.status(200).body(new ApiResponse("Category Deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }
}

