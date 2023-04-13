package com.ajru.pharmacy_product_system.business.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportByClassDto {
    @JsonProperty("class")
    private String classification;

    @JsonProperty("gross")
    private String gross;

    @JsonProperty("profit")
    private String profit;
}
