package org.example.ecommercejpa_ex.Repository;

import org.example.ecommercejpa_ex.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
