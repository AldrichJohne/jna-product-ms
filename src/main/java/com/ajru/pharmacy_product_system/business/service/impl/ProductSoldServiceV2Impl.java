package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import com.ajru.pharmacy_product_system.business.service.ProductSoldServiceV2;
import com.ajru.pharmacy_product_system.business.util.InvoiceNumberGenerator;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSoldServiceV2Impl implements ProductSoldServiceV2 {

    private final ProductSoldService productSoldService;
    private final InvoiceNumberGenerator invoiceNumberGenerator;
    private final Logger logger;

    public ProductSoldServiceV2Impl(ProductSoldService productSoldService, InvoiceNumberGenerator invoiceNumberGenerator) {
        this.productSoldService = productSoldService;
        this.invoiceNumberGenerator = invoiceNumberGenerator;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public List<ProductSoldDto> batchSell(List<ProductSoldDto> productSoldDtoList) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "setting up multiple selling of products");

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calling generateInvoice method to generate invoice number", currentMethodName);
        final String invoiceCode = this.generateInvoice();

        final List<ProductSold> finalReturn = new ArrayList<>();

        final List<ProductSold> productSoldList = productSoldDtoList.stream()
                .map(ProductSold::from)
                .collect(Collectors.toList());

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "putting invoice number on every productSold object", currentMethodName);
        for (ProductSold productSold : productSoldList) {
            productSold.setInvoiceCode(invoiceCode);
            this.productSoldService.sellProduct(productSold, Boolean.valueOf(productSold.getIsDiscounted()));
            finalReturn.add(productSold);
        }

        return finalReturn.stream()
                .map(ProductSoldDto::from)
                .collect(Collectors.toList());

    }

    protected String generateInvoice() {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "calling invoiceNumber method in InvoiceNumberGenerator class");
        return this.invoiceNumberGenerator.invoiceNumber();
    }
}
