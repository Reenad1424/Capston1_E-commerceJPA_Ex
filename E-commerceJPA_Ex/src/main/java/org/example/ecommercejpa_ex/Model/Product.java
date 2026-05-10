package org.example.ecommercejpa_ex.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null")
    @Size(min = 4, message = "Product name must be more than 3 characters long")
    private String name;

    @Column(columnDefinition = "double not null check(price > 0)")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @Column(columnDefinition = "int not null")
    private Integer categoryId;
}

