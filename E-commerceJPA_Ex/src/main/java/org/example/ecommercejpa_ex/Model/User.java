package org.example.ecommercejpa_ex.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null unique")
    @Size(min = 6, message = "Username must be more than 5 characters long")
    private String userName;

    @Column(columnDefinition = "varchar(255) not null")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain both characters and digits")
    private String password;

    @Column(columnDefinition = "varchar(50) not null unique")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(columnDefinition = "varchar(10) not null check(role='Admin' or role='Customer')")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be either 'Admin' or 'Customer'")
    private String role;

    @Column(columnDefinition = "double not null check(balance >= 0)")
    @PositiveOrZero(message = "Balance must be 0 or a positive number")
    private Double balance;

    @Column(columnDefinition = "double not null default 0")
    private Double totalSpent = 0.0;

    @Column(columnDefinition = "boolean default false")
    private Boolean isVip = false;
}

