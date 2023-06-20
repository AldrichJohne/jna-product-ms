package com.ajru.pharmacy_product_system.business.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInfoDto {
    @JsonProperty("businessName")
    private String businessName;

    @JsonProperty("businessAlias")
    private String businessAlias;

    @JsonProperty("businessAddress")
    private String businessAddress;

    @JsonProperty("businessTin")
    private String businessTin;
}
