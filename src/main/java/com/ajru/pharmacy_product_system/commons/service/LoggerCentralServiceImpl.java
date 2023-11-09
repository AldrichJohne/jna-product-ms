package com.ajru.pharmacy_product_system.commons.service;

import com.ajru.pharmacy_product_system.commons.constants.LogType;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoggerCentralServiceImpl implements LoggerCentralService {

    private final Logger logger;

    public LoggerCentralServiceImpl() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void logApiRequest(LogType logType, String description, String method, String url) {
        final String message = String.format(
                StringConstants.WEB_REQ.getValue(),
                description,
                method,
                url
        );

        this.log(logType, message);
    }

    @Override
    public void logApiResponse(LogType logType, Object response) {
        final String message = String.format(
                StringConstants.WEB_RESP.getValue(),
                response
        );
        this.log(logType, message);
    }

    @Override
    public void logException(Exception exception) {
        logger.error("Exception occurred:", exception);
    }

    private void log(LogType logType, String message) {
        switch (logType) {
            case DEBUG:
                logger.debug(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            default:
                logger.info(message);
        }
    }
}
