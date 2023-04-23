package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.mapper.CategoryMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.repository.CategoryRepository;
import com.cs490.shoppingCart.ProductManagementModule.service.Imp.CategoryServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImp categoryServiceImp;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    public void testGetAllCategories() throws ItemNotFoundException {

//        List<Category> categoryList = categoryRepository.findAll();
//
//        if (categoryList.isEmpty()) {
//            throw new ItemNotFoundException("No category found in database");
//        }
//        return categoryList;

        Category category = new Category();
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> categoryListResult = categoryServiceImp.getAllCategories();
        assertThat(categoryListResult).isEqualTo(categoryList);

    }

    @Test
    public void testGetAllCategoriesWithEmptyList() {

        List<Category> emptyCategoryList = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(emptyCategoryList);

        try {
            categoryServiceImp.getAllCategories();
        } catch (ItemNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No category found in database");
        }
    }

    @Test
    public void testCreateCategory() {

        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Electronics");
        category.setDescription("includes products such as smartphones, laptops, TVs, cameras, and other electronic devices");

        categoryServiceImp.createCategory(category);
        verify(categoryRepository).save(category);
    }

    @Test
    public void testGetCategoryById() throws ItemNotFoundException {

        Long categoryId = 1L;
        Category category = new Category();
        category.setCategoryId(categoryId);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.fromCategoryResponseToDomain(category)).thenReturn(categoryResponse);

        CategoryResponse result = categoryServiceImp.getCategoryById(categoryId);

        assertThat(result.getCategoryId()).isEqualTo(category.getCategoryId());
    }

    @Test
    public void testGetCategoryByIdNotFound() {

        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        try {
            categoryServiceImp.getCategoryById(1L);
        } catch (ItemNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No category found with id: " +id);
        }
    }

    @Test
    public void testDeleteCategoryById() {

        Long id = 1L;

        Category category = new Category();
        category.setCategoryId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        boolean isDelete = categoryServiceImp.deleteCategoryById(id);

        assertThat(isDelete).isTrue();
    }

    @Test
    public void testDeleteCategoryByIdNotFound() {

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean isDelete = categoryServiceImp.deleteCategoryById(1L);
        assertThat(isDelete).isFalse();
    }

    @Test
    public void testDeleteCategoryByIdWithEmptyResultDataAccessException() {

        Long id = 1L;

        // mock the behavior of the repository
        when(categoryRepository.findById(id)).thenReturn(Optional.of(new Category()));
        doThrow(new EmptyResultDataAccessException(1)).when(categoryRepository).deleteById(id);

        // test the method
        boolean result = categoryServiceImp.deleteCategoryById(id);
        assertThat(result).isFalse();

        // verify that the repository methods were called
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateCategoryByIdNotFound() throws ItemNotFoundException {

        Long id = 1L;
        Category category = new Category();

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        try {
            categoryServiceImp.updateCategory(category, id);
        } catch (ItemNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Not found for id: " + id);
        } catch (IdNotMatchException e) {
            throw new ItemNotFoundException("Not match id from url with input id");
        }
    }

    @Test
    public void testUpdateCategoryIdNotMatch() throws ItemNotFoundException {

        Long id = 1L;
        Category category = new Category();
        category.setCategoryId(id);

        Long unMatchedId = 2L;
        Category unMatchedCategory = new Category();
        unMatchedCategory.setCategoryId(unMatchedId);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(unMatchedCategory));

        try {
            categoryServiceImp.updateCategory(unMatchedCategory, id);
        } catch (IdNotMatchException e) {
            assertThat(e.getMessage()).isEqualTo("Not match id from url with input id");
        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException("Not found for id: " + id);
        }
    }

    @Test
    public void testUpdateCategoryById() throws IdNotMatchException, ItemNotFoundException {

        Long id = 1L;
        Category category = new Category();
        category.setCategoryId(id);

        Category categoryReturn = new Category();
        category.setCategoryId(id);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(categoryReturn));
        when(categoryMapper.fromCategoryResponseToDomain(category)).thenReturn(categoryResponse);

        categoryServiceImp.updateCategory(category, id);

        verify(categoryRepository).save(category);
    }

}
