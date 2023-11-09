package com.ajru.pharmacy_product_system.business.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericGoodResponseDto {
    private String responseTitle;
    private String responseDescription;
    private Object responseObject;
}
