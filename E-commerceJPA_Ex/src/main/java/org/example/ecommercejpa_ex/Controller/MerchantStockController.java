package org.example.ecommercejpa_ex.Controller;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Service.MerchantStockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.example.ecommercejpa_ex.Api.ApiResponse;
import org.example.ecommercejpa_ex.Model.MerchantStock;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(merchantStockService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        int result = merchantStockService.add(merchantStock);

        if (result == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock added successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Merchant ID or Product ID not found"));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if (merchantStockService.update(id, merchantStock)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock updated successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (merchantStockService.delete(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock not found"));
    }

    @PutMapping("/add-stock/{merchantId}/{productId}/{amount}")
    public ResponseEntity<?> addMoreStock(@PathVariable Integer merchantId, @PathVariable Integer productId, @PathVariable int amount) {
        int result = merchantStockService.moreStock(merchantId, productId, amount);

        if (result == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Stock increased successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant does not have this product in stock or record not found"));
    }

    // Get low stock
    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStock() {
        List<MerchantStock> low = merchantStockService.getLowStock();
        if (low.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No low stock found"));
        return ResponseEntity.status(200).body(low);
    }

    // Transfer Stock
    @PutMapping("/transfer/{fromMId}/{toMId}/{pId}/{amount}")
    public ResponseEntity<?> transfer(@PathVariable Integer fromMId, @PathVariable Integer toMId,
                                      @PathVariable Integer pId, @PathVariable int amount) {
        int res = merchantStockService.transferStock(fromMId, toMId, pId, amount);
        if (res == 1)
            return ResponseEntity.status(200).body(new ApiResponse("Transfer successful"));
        if (res == -1)
            return ResponseEntity.status(400).body(new ApiResponse("Merchant stock records not found"));
        return ResponseEntity.status(400).body(new ApiResponse("Insufficient stock to transfer"));
    }
    // Total Inventory Value
    @GetMapping("/total-value/{merchantId}")
    public ResponseEntity<?> getTotalValue(@PathVariable Integer merchantId) {
        Double total = merchantStockService.getTotalInventoryValue(merchantId);
        if (total == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant has no stock or record not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Total inventory value: " + total));
    }

}
