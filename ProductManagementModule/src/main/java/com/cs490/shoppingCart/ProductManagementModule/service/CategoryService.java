package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.mapper.CategoryMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;


    public Category createCategory(Category category){

        return categoryRepository.save(category);
    }
    public CategoryResponse updateCategory(Category category, Long categoryId) throws ItemNotFoundException, IdNotMatchException {

        Optional<Category> categoryToBeModified = categoryRepository.findById(categoryId);

        if (categoryToBeModified.isEmpty()) {
            throw new ItemNotFoundException("Not found for id: " + categoryId );
        }

        if (categoryId != category.getCategoryId()) {
           throw new IdNotMatchException("Not match id from url with input id");
        }

        Category categoryUpdated = categoryRepository.save(category);
        CategoryResponse categoryResponse = categoryMapper.fromCategoryResponseToDomain(categoryUpdated);

        return categoryResponse;
    }
    public List<Category> getAllCategories(){

        return categoryRepository.findAll();
    }

    public CategoryResponse getCategoryById(Long id) throws ItemNotFoundException {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            Category categoryResult = category.get();
            CategoryResponse categoryResponse = categoryMapper.fromCategoryResponseToDomain(categoryResult);
            return categoryResponse;
        } else {
            throw new ItemNotFoundException("No category found with id: " +id);
        }
    }

    public boolean deleteCategoryById(Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            return false;
        }

        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
    }
}
