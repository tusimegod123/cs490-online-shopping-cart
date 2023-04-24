package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryRequest;
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

        // Arrange
        Category category1 = new Category();
        category1.setCategoryId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setCategoryId(2L);
        category2.setName("Category 2");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        CategoryResponse categoryResponse1 = new CategoryResponse();
        categoryResponse1.setCategoryId(1L);
        categoryResponse1.setName("Category 1");

        CategoryResponse categoryResponse2 = new CategoryResponse();
        categoryResponse2.setCategoryId(2L);
        categoryResponse2.setName("Category 2");

        List<CategoryResponse> expectedCategoryResponses = new ArrayList<>();
        expectedCategoryResponses.add(categoryResponse1);
        expectedCategoryResponses.add(categoryResponse2);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryMapper.convertCategoryToResponse(category1)).thenReturn(categoryResponse1);
        when(categoryMapper.convertCategoryToResponse(category2)).thenReturn(categoryResponse2);

        // Act
        List<CategoryResponse> actualCategoryResponses = categoryServiceImp.getAllCategories();

        // Assert
        assertThat(expectedCategoryResponses).isEqualTo(actualCategoryResponses);

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

        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Electronics");
        categoryRequest.setDescription("includes products such as smartphones, laptops, TVs, cameras, and other electronic devices");

        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Electronics");
        category.setDescription("includes products such as smartphones, laptops, TVs, cameras, and other electronic devices");

        CategoryResponse expectedCategoryResponse = new CategoryResponse();
        expectedCategoryResponse.setCategoryId(1L);
        expectedCategoryResponse.setName("Electronics");

        when(categoryMapper.convertRequestToCategory(categoryRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.convertCategoryToResponse(category)).thenReturn(expectedCategoryResponse);

        // Act
        categoryServiceImp.createCategory(categoryRequest);

        // Verify
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
        when(categoryMapper.convertCategoryToResponse(category)).thenReturn(categoryResponse);

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

        Long idRequest = 1L;
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(idRequest);
        categoryRequest.setName("Electronics");
        categoryRequest.setDescription("This category includes all electronic devices such as smartphones, laptops, televisions...");

        when(categoryRepository.findById(idRequest)).thenReturn(Optional.empty());

        try {
            categoryServiceImp.updateCategory(categoryRequest, idRequest);
        } catch (ItemNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Not found for id: " + idRequest);
        } catch (IdNotMatchException e) {
            throw new ItemNotFoundException("Not match id from url with input id");
        }
    }

    @Test
    public void testUpdateCategoryIdNotMatch() throws ItemNotFoundException {

        Long id = 2L;
        Category category = new Category();
        category.setCategoryId(id);
        category.setName("Electronics");
        category.setDescription("This category includes all electronic devices such as smartphones, laptops, televisions...");

        Long unMatchedId = 3L;
        Category unMatchedCategory = new Category();
        unMatchedCategory.setCategoryId(unMatchedId);
        unMatchedCategory.setName("Health & Care");
        unMatchedCategory.setDescription("This category includes all supplement products");

        Long idRequest = 2L;
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(idRequest);
        categoryRequest.setName("Electronics");
        categoryRequest.setDescription("This category includes all electronic devices such as smartphones, laptops, televisions...");


        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        when(categoryMapper.convertRequestToCategory(categoryRequest)).thenReturn(category);

        try {
            categoryServiceImp.updateCategory(categoryRequest, unMatchedId);
        } catch (IdNotMatchException e) {
            assertThat(e.getMessage()).isEqualTo("Not match id from url with input id");
        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException("Not found for id: " + unMatchedId);
        }
    }

    @Test
    public void testUpdateCategoryById() throws IdNotMatchException, ItemNotFoundException {

        Long id = 1L;
        Category category = new Category();
        category.setCategoryId(id);
        category.setName("Electronics");
        category.setDescription("This category includes all electronic devices such as smartphones, laptops, televisions...");

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(id);
        categoryRequest.setName("Electronics");
        categoryRequest.setDescription("This category includes all electronic devices such as smartphones, laptops, televisions...");


        Category categoryReturn = new Category();
        categoryReturn.setCategoryId(id);
        categoryReturn.setName("Electronics");
        categoryReturn.setDescription("This category includes all electronic devices such as smartphones, laptops, televisions...");


        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(id);
        categoryResponse.setName("Electronics");
        categoryResponse.setDescription("This category includes all electronic devices such as smartphones, laptops, televisions...");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(categoryReturn));
        when(categoryMapper.convertRequestToCategory(categoryRequest)).thenReturn(category);
        when(categoryMapper.convertCategoryToResponse(category)).thenReturn(categoryResponse);
        categoryServiceImp.updateCategory(categoryRequest, id);

        verify(categoryRepository).save(category);
    }

}
