package org.example.ecommercejpa_ex.Repository;

import org.example.ecommercejpa_ex.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
