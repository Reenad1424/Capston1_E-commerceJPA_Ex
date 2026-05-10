package org.example.ecommercejpa_ex.Controller;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.example.ecommercejpa_ex.Api.ApiResponse;
import org.example.ecommercejpa_ex.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(userService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.add(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (userService.update(id, user))
            return ResponseEntity.status(200).body(new ApiResponse("User Updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (userService.delete(id))
            return ResponseEntity.status(200).body(new ApiResponse("User Deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable Integer userId,
                                        @PathVariable Integer productId,
                                        @PathVariable Integer merchantId) {

        int result = userService.buyProduct(userId, productId, merchantId);

        return switch (result) {
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Purchase successful"));
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("User ID not found"));
            case -2 -> ResponseEntity.status(400).body(new ApiResponse("Product ID not found"));
            case -3 -> ResponseEntity.status(400).body(new ApiResponse("Merchant does not have this product"));
            case -4 -> ResponseEntity.status(400).body(new ApiResponse("Product out of stock"));
            case -5 -> ResponseEntity.status(400).body(new ApiResponse("Insufficient balance"));
            default -> ResponseEntity.status(400).body(new ApiResponse("An error occurred"));
        };
    }

    @PutMapping("/return/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> returnProduct(@PathVariable Integer userId,
                                           @PathVariable Integer productId,
                                           @PathVariable Integer merchantId) {
        int result = userService.returnProduct(userId, productId, merchantId);

        return switch (result) {
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Product returned successfully"));
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case -2 -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case -4 -> ResponseEntity.status(400).body(new ApiResponse("Merchant stock record not found"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Error occurred"));
        };
    }

    @GetMapping("/get-by-role/{role}")
    public ResponseEntity<?> getByRole(@PathVariable String role) {
        return ResponseEntity.status(200).body(userService.getUsersByRole(role));
    }

    @GetMapping("/coupon/{userId}")
    public ResponseEntity<?> getCoupon(@PathVariable Integer userId) {
        String coupon = userService.generateCoupon(userId);
        if (coupon.equals("No coupon available")) {
            return ResponseEntity.status(400).body(new ApiResponse(coupon));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Your VIP Coupon: " + coupon));
    }
}

