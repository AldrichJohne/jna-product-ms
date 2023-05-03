package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;

import java.util.List;

public interface ProductServiceV2 {
    List<ProductDto> setUpProducts(List<ProductDto> products);
}
