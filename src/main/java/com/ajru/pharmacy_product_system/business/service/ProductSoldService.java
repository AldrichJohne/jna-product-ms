package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;

import java.util.List;

public interface ProductSoldService {
    ProductSold sellProduct(Long id, ProductSold productSold);

        List<ProductSold> getProductSold();
}
