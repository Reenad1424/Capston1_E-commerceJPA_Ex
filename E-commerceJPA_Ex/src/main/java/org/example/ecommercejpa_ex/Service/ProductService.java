package org.example.ecommercejpa_ex.Service;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Model.Category;
import org.example.ecommercejpa_ex.Model.Product;
import org.example.ecommercejpa_ex.Repository.CategoryRepository;
import org.example.ecommercejpa_ex.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

        public List<Product> get() {
            return productRepository.findAll();
        }

        public int add(Product product) {

            boolean categoryExists = categoryRepository.existsById(product.getCategoryId());

            if (categoryExists) {
                productRepository.save(product);
                return 1;
            }
            return -1;
        }

        public Boolean update(Integer id, Product product) {
            Product oldProduct = productRepository.findById(id).orElse(null);
            if (oldProduct == null) return false;


            if (!categoryRepository.existsById(product.getCategoryId())) return false;

            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setCategoryId(product.getCategoryId());

            productRepository.save(oldProduct);
            return true;
        }

        public Boolean delete(Integer id) {
            Product product = productRepository.findById(id).orElse(null);
            if (product == null) return false;

            productRepository.delete(product);
            return true;
        }

        // 1. Get by Category Name 
        public List<Product> getByCategoryName(String categoryName) {
            List<Category> categories = categoryRepository.findAll();
            Integer foundCategoryId = null;


            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getName().equalsIgnoreCase(categoryName)) {
                    foundCategoryId = categories.get(i).getId();
                    break;
                }
            }
            if (foundCategoryId == null) return null;

            List<Product> allProducts = productRepository.findAll();
            List<Product> filteredProducts = new ArrayList<>();


            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getCategoryId().equals(foundCategoryId)) {
                    filteredProducts.add(allProducts.get(i));
                }
            }
            return filteredProducts;
        }

        // 2. Get Cheapest Products (Bubble Sort)
        public List<Product> getCheapestProducts(String categoryName) {
            List<Product> filteredProducts = getByCategoryName(categoryName);
            if (filteredProducts == null || filteredProducts.isEmpty()) return null;

            // Bubble Sort  
            for (int i = 0; i < filteredProducts.size() - 1; i++) {
                for (int j = 0; j < filteredProducts.size() - i - 1; j++) {
                    if (filteredProducts.get(j).getPrice() > filteredProducts.get(j + 1).getPrice()) {
                        Product temp = filteredProducts.get(j);
                        filteredProducts.set(j, filteredProducts.get(j + 1));
                        filteredProducts.set(j + 1, temp);
                    }
                }
            }
            return filteredProducts;
        }

        // 3. Get Most Expensive Products 
        public List<Product> getMostExpensiveProducts(String categoryName) {
            List<Product> filteredProducts = getByCategoryName(categoryName);
            if (filteredProducts == null || filteredProducts.isEmpty()) return null;

            // Bubble Sort 
            for (int i = 0; i < filteredProducts.size() - 1; i++) {
                for (int j = 0; j < filteredProducts.size() - i - 1; j++) {
                    if (filteredProducts.get(j).getPrice() < filteredProducts.get(j + 1).getPrice()) {
                        Product temp = filteredProducts.get(j);
                        filteredProducts.set(j, filteredProducts.get(j + 1));
                        filteredProducts.set(j + 1, temp);
                    }
                }
            }
            return filteredProducts;
        }
    }
