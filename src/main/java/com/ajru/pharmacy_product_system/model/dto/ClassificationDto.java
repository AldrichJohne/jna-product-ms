package com.ajru.pharmacy_product_system.model.dto;

import com.ajru.pharmacy_product_system.model.Classification;
import com.ajru.pharmacy_product_system.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClassificationDto {

    private Long id;
    private String name;
    private List<ProductDto> productsDto = new ArrayList<>();

    //Classification object to classificationDto
    public static ClassificationDto from(Classification classification) {
        ClassificationDto classificationDto = new ClassificationDto();
        classificationDto.setId(classification.getId());
        classificationDto.setName((classification.getName()));
        classificationDto.setProductsDto(classification.getProducts().stream().map(ProductDto::from).collect(Collectors.toList()));
        return classificationDto;
    }
}