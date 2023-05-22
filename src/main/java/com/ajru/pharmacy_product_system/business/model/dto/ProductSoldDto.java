package com.ajru.pharmacy_product_system.business.model.dto;

import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductSoldDto {

    private Long id;
    private Long productId;
    private String classification;
    private String productName;
    private double price;
    private double srp;
    private int soldQuantity;
    private double amount;
    private double profit;
    private LocalDate transactionDate;
    private String isDiscounted;
    private String pharmacist;
    private String invoiceCode;

    //transform productSold to productSoldDto
    public static ProductSoldDto from(ProductSold productSold) {
        ProductSoldDto productSoldDto = new ProductSoldDto();
        productSoldDto.setId(productSold.getId());
        productSoldDto.setClassification(productSold.getClassification());
        productSoldDto.setProductName(productSold.getProductName());
        productSoldDto.setPrice(productSold.getPrice());
        productSoldDto.setSrp(productSold.getSrp());
        productSoldDto.setSoldQuantity(productSold.getSoldQuantity());
        productSoldDto.setAmount(productSold.getAmount());
        productSoldDto.setProfit(productSold.getProfit());
        productSoldDto.setTransactionDate(productSold.getTransactionDate());
        productSoldDto.setIsDiscounted(productSold.getIsDiscounted());
        productSoldDto.setProductId(productSold.getProductId());
        productSoldDto.setPharmacist(productSold.getPharmacist());
        productSoldDto.setInvoiceCode(productSold.getInvoiceCode());

        return productSoldDto;
    }

}
