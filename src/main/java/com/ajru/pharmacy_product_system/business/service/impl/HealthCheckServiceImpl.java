package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.HealthCheckDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class HealthCheckServiceImpl {

    private final String applicationName;
    private final String applicationVersion;
    private final String port;

    public HealthCheckServiceImpl(
            @Value("${info.app.name}") final String applicationName,
            @Value("${info.app.version}") final String applicationVersion,
            @Value("${server.port}") final String port) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.port = port;
    }

    public HealthCheckDto healthCheck(final HttpServletRequest request) {
        return new HealthCheckDto(
                this.applicationName,
                this.applicationVersion,
                this.port,
                String.valueOf(request.getRequestURL())
        );
    }
}
