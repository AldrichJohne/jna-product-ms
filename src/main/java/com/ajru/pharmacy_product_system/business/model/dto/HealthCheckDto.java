package com.ajru.pharmacy_product_system.business.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheckDto {

    @JsonProperty("applicationName")
    private String applicationName;

    @JsonProperty("applicationVersion")
    private String applicationVersion;

    @JsonProperty("port")
    private String port;

    @JsonProperty("requestFrom")
    private String requestFrom;

    @JsonProperty("businessInfo")
    private BusinessInfoDto businessInfo;
}
