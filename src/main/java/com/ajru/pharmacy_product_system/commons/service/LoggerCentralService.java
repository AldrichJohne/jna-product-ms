package com.ajru.pharmacy_product_system.commons.service;

import com.ajru.pharmacy_product_system.commons.constants.LogType;

public interface LoggerCentralService {
    void logApiRequest(LogType logType, String description, String method, String url);

    void logApiResponse(LogType logType, Object response);

    void logException(Exception exception);
}
