package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;

import java.util.List;

public interface ProductSoldServiceV2 {
    List<ProductSoldDto> batchSell(List<ProductSoldDto> productSoldDtoList);
}
