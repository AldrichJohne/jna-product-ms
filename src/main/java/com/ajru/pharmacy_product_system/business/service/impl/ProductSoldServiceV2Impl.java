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

import java.security.NoSuchAlgorithmException;
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
    public List<ProductSoldDto> sellMultipleProductsSimultaneously(
            final List<ProductSoldDto> lstProductSoldDto) {

        final String invoiceCode = this.invoiceNumberGenerator.invoiceNumber();

        final List<ProductSold> finalReturn = new ArrayList<>();

        final List<ProductSold> productSoldList = lstProductSoldDto.stream()
                .map(ProductSold::from)
                .collect(Collectors.toList());

        for (final ProductSold productSold : productSoldList) {
            productSold.setInvoiceCode(invoiceCode);
            this.productSoldService.sellProduct(productSold, Boolean.valueOf(productSold.getIsDiscounted()));
            finalReturn.add(productSold);
        }

        return finalReturn.stream()
                .map(ProductSoldDto::from)
                .collect(Collectors.toList());

    }

}
