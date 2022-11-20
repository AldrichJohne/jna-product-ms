package com.ajru.pharmacy_product_system.business.model.dto;

import com.ajru.pharmacy_product_system.business.model.entity.Product;
import lombok.Data;

@Data
public class PlainProductDto {
    private Long id;
    private String name;

    public static PlainProductDto from(Product product) {
        PlainProductDto plainProductDto = new PlainProductDto();
        plainProductDto.setId(product.getId());
        plainProductDto.setName(product.getName());
        return plainProductDto;
    }
}
