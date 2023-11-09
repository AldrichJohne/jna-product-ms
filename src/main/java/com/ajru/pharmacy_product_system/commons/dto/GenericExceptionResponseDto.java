package com.ajru.pharmacy_product_system.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericExceptionResponseDto {

    private String errorCode;
    private int status;
    private String message;
}
