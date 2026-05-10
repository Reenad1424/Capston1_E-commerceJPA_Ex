package org.example.ecommercejpa_ex.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MerchantStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    private Integer productId;

    @Column(columnDefinition = "int not null")
    private Integer merchantId;

    @Column(columnDefinition = "int not null check(stock > 10)")
    @Min(value = 11, message = "Stock must be more than 10 at start")
    private Integer stock;
}

