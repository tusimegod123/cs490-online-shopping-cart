package com.cs490.shoppingCart.ProductManagementModule.service.Imp;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.mapper.CategoryMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.repository.CategoryRepository;
import com.cs490.shoppingCart.ProductManagementModule.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;


    public CategoryResponse createCategory(CategoryRequest categoryRequest){

        Category category = categoryMapper.convertRequestToCategory(categoryRequest);
        category = categoryRepository.save(category);
        CategoryResponse categoryResponse = categoryMapper.convertCategoryToResponse(category);

        return categoryResponse;
    }
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long categoryId) throws ItemNotFoundException, IdNotMatchException {

        Optional<Category> categoryToBeModified = categoryRepository.findById(categoryId);

        if (!categoryId.equals(categoryRequest.getId())) {
            throw new IdNotMatchException("Not match id from url with input id");
        }

        if (categoryToBeModified.isEmpty()) {
            throw new ItemNotFoundException("Not found for id: " + categoryId );
        }

        Category category = categoryMapper.convertRequestToCategory(categoryRequest);
        category.setCategoryId(categoryRequest.getId());
        Category categoryUpdated = categoryRepository.save(category);
        CategoryResponse categoryResponse = categoryMapper.convertCategoryToResponse(categoryUpdated);

        return categoryResponse;
    }

    public List<CategoryResponse> getAllCategories() throws ItemNotFoundException {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryResponse categoryResponse = categoryMapper.convertCategoryToResponse(category);
            categoryResponses.add(categoryResponse);
        }

        return categoryResponses;
    }

    public CategoryResponse getCategoryById(Long id) throws ItemNotFoundException {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            Category categoryResult = category.get();
            CategoryResponse categoryResponse = categoryMapper.convertCategoryToResponse(categoryResult);
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
