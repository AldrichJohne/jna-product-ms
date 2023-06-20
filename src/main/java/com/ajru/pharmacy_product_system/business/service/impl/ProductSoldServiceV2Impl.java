package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import com.ajru.pharmacy_product_system.business.service.ProductSoldServiceV2;
import com.ajru.pharmacy_product_system.business.util.InvoiceNumberGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSoldServiceV2Impl implements ProductSoldServiceV2 {

    private final ProductSoldService productSoldService;
    private final InvoiceNumberGenerator invoiceNumberGenerator;

    public ProductSoldServiceV2Impl(ProductSoldService productSoldService, InvoiceNumberGenerator invoiceNumberGenerator) {
        this.productSoldService = productSoldService;
        this.invoiceNumberGenerator = invoiceNumberGenerator;
    }

    @Override
    public List<ProductSoldDto> batchSell(List<ProductSoldDto> productSoldDtoList) {
        final String invoiceCode = this.generateInvoice();
        final List<ProductSold> finalReturn = new ArrayList<>();

        final List<ProductSold> productSoldList = productSoldDtoList.stream()
                .map(ProductSold::from)
                .collect(Collectors.toList());

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
        return this.invoiceNumberGenerator.invoiceNumber();
    }
}
