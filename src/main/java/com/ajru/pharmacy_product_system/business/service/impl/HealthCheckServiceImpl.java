package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.BusinessInfoDto;
import com.ajru.pharmacy_product_system.business.model.dto.HealthCheckDto;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class HealthCheckServiceImpl {

    private final String applicationName;
    private final String applicationVersion;
    private final String port;
    private final String businessName;
    private final String businessAlias;
    private final String businessAddress;
    private final String businessTin;
    private final Logger logger;

    public HealthCheckServiceImpl(
            @Value("${info.app.name}") final String applicationName,
            @Value("${info.app.version}") final String applicationVersion,
            @Value("${server.port}") final String port,
            @Value("${business.name}") final String businessName,
            @Value("${business.alias}") final String businessAlias,
            @Value("${business.address}") final String businessAddress,
            @Value("${business.tin}") final String businessTin) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.port = port;
        this.businessName = businessName;
        this.businessAlias = businessAlias;
        this.businessAddress = businessAddress;
        this.businessTin = businessTin;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public HealthCheckDto healthCheck(final HttpServletRequest request) {
        return new HealthCheckDto(
                this.applicationName,
                this.applicationVersion,
                this.port,
                String.valueOf(request.getRequestURL()),
                new BusinessInfoDto(
                        this.businessName,
                        this.businessAlias,
                        this.businessAddress,
                        this.businessTin
                )
        );
    }
}
