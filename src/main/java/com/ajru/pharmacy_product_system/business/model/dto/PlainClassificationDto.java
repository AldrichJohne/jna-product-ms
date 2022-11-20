package com.ajru.pharmacy_product_system.business.model.dto;

import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import lombok.Data;

@Data
public class PlainClassificationDto {
    private Long id;
    private String name;

    public static PlainClassificationDto from(Classification classification) {
        PlainClassificationDto plainClassificationDto = new PlainClassificationDto();
        plainClassificationDto.setId(classification.getId());
        plainClassificationDto.setName(classification.getName());
        return plainClassificationDto;
    }
}
