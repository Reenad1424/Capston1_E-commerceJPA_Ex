package org.example.ecommercejpa_ex.Repository;

import org.example.ecommercejpa_ex.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MerchantStockRepository extends JpaRepository<MerchantStock,Integer> {
}
