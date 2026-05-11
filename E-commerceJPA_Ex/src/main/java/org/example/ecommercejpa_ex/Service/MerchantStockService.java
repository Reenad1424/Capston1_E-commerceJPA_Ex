package org.example.ecommercejpa_ex.Service;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Model.MerchantStock;
import org.example.ecommercejpa_ex.Model.Product;
import org.example.ecommercejpa_ex.Repository.MerchantRepository;
import org.example.ecommercejpa_ex.Repository.MerchantStockRepository;
import org.example.ecommercejpa_ex.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final MerchantStockRepository merchantStockRepository;

     private final ProductRepository productRepository;
        private final MerchantRepository merchantRepository;

        public List<MerchantStock> get() {
            return merchantStockRepository.findAll();
        }

        public int add(MerchantStock merchantStock) {

            boolean productExists = productRepository.existsById(merchantStock.getProductId());
            boolean merchantExists = merchantRepository.existsById(merchantStock.getMerchantId());

            if (productExists && merchantExists) {
                merchantStockRepository.save(merchantStock);
                return 1;
            }
            return -1;
        }

        public Boolean update(Integer id, MerchantStock merchantStock) {
            MerchantStock oldStock = merchantStockRepository.findById(id).orElse(null);
            if (oldStock == null) return false;

            oldStock.setStock(merchantStock.getStock());
            oldStock.setProductId(merchantStock.getProductId());
            oldStock.setMerchantId(merchantStock.getMerchantId());

            merchantStockRepository.save(oldStock);
            return true;
        }

        public Boolean delete(Integer id) {
            MerchantStock stock = merchantStockRepository.findById(id).orElse(null);
            if (stock == null) return false;

            merchantStockRepository.delete(stock);
            return true;
        }

    public int moreStock(Integer merchantId, Integer productId, int amount) {
        List<MerchantStock> stocks = merchantStockRepository.findAll();

        for (int i = 0; i < stocks.size(); i++) {
            MerchantStock s = stocks.get(i);
            if (s.getMerchantId().equals(merchantId) && s.getProductId().equals(productId)) {
                s.setStock(s.getStock() + amount);
                merchantStockRepository.save(s);
                return 1;
            }
        }
        return 0     ;
    }

    // 2. Get Low Stock 
    public List<MerchantStock> getLowStock() {
        List<MerchantStock> allStocks = merchantStockRepository.findAll();
        List<MerchantStock> lowStocks = new ArrayList<>();

        for (int i = 0; i < allStocks.size(); i++) {
            if (allStocks.get(i).getStock() <= 10) {
                lowStocks.add(allStocks.get(i));
            }
        }
        return lowStocks;
    }

    // 3. Transfer Stock 
    public int transferStock(Integer fromMId, Integer toMId, Integer pId, int amount) {
        List<MerchantStock> stocks = merchantStockRepository.findAll();
        MerchantStock fromS = null;
        MerchantStock toS = null;

        for (int i = 0; i < stocks.size(); i++) {
            MerchantStock s = stocks.get(i);
            if (s.getProductId().equals(pId)) {
                if (s.getMerchantId().equals(fromMId)) fromS = s;
                if (s.getMerchantId().equals(toMId)) toS = s;
            }
        }

        if (fromS == null || toS == null) return -1;
        if (fromS.getStock() < amount) return -2;

        fromS.setStock(fromS.getStock() - amount);
        toS.setStock(toS.getStock() + amount);

        merchantStockRepository.save(fromS);
        merchantStockRepository.save(toS);
        return 1;
    }

    // 4. Total Inventory Value
    public Double getTotalInventoryValue(Integer merchantId) {
        List<MerchantStock> allStocks = merchantStockRepository.findAll();
        List<org.example.ecommercejpa_ex.Model.Product> allProducts = productRepository.findAll();

        double totalValue = 0;
        boolean merchantHasStock = false;

        for (int i = 0; i < allStocks.size(); i++) {
            MerchantStock s = allStocks.get(i);
            if (s.getMerchantId().equals(merchantId)) {
                merchantHasStock = true;


                for (int j = 0; j < allProducts.size(); j++) {
                    if (allProducts.get(j).getId().equals(s.getProductId())) {
                        totalValue += (allProducts.get(j).getPrice() * s.getStock());
                        break;
                    }
                }
            }
        }
        return merchantHasStock ? totalValue : null;
    }
}




