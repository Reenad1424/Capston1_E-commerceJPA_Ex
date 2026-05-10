package org.example.ecommercejpa_ex.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Model.Category;
import org.example.ecommercejpa_ex.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


        public List<Category> get() {
            return categoryRepository.findAll();
        }

        public void add(Category category) {
            categoryRepository.save(category);
        }

        public Boolean update(Integer id, Category category) {
            Category oldCategory = categoryRepository.findById(id).orElse(null);

            if (oldCategory == null) {
                return false;
            }

            oldCategory.setName(category.getName());
            categoryRepository.save(oldCategory);
            return true;
        }

        public Boolean delete(Integer id) {
            Category category = categoryRepository.findById(id).orElse(null);

            if (category == null) {
                return false;
            }

            categoryRepository.delete(category);
            return true;
        }
    }


