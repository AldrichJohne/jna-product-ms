package com.ajru.pharmacy_product_system.business.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyMonthlyReportDto {

    @JsonProperty("date")
    private String date;

    @JsonProperty("profit")
    private String profit;

    @JsonProperty("gross")
    private String gross;
}
