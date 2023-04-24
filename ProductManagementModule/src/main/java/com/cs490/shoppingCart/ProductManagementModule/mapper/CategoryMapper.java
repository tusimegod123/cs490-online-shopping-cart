package com.cs490.shoppingCart.ProductManagementModule.mapper;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

//    @Mapping(target = "categoryId", ignore = true)
    Category convertRequestToCategory(CategoryRequest categoryRequest);


    CategoryResponse convertCategoryToResponse(Category category);

    Category convertResponseToCategory(CategoryResponse categoryResponse);
}
