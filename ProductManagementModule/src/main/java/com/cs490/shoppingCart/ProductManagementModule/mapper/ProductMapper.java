package com.cs490.shoppingCart.ProductManagementModule.mapper;

import com.cs490.shoppingCart.ProductManagementModule.dto.CreateProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "productId", ignore = true)
    Product fromCreateProductRequestToDomain(CreateProductRequest createProductRequest);
}
