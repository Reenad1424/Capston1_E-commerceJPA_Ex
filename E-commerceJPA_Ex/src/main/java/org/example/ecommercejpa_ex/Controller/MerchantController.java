package org.example.ecommercejpa_ex.Controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Api.ApiResponse;
import org.example.ecommercejpa_ex.Model.Merchant;
import org.example.ecommercejpa_ex.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.status(200).body(merchantService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        merchantService.add(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if(merchantService.update(id, merchant))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if(merchantService.delete(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }
}

