package com.ajru.pharmacy_product_system.business.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheckDto {
    private String applicationName;
    private String applicationVersion;
    private String port;
    private String requestFrom;
}
