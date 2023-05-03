package com.ajru.pharmacy_product_system.business.model.dto;

import com.ajru.pharmacy_product_system.business.model.entity.Product;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private long remainingStock;
    private long totalStock;
    private long sold;
    private double pricePerPc;
    private double srpPerPc;
    private double totalPriceRemaining;
    private double totalPriceSold;
    private String gross;
    private double profit;
    private LocalDate expiryDate;
    private String className;
    private Long classId;
    private PlainClassificationDto plainClassificationDto;

    //transform product to productDto
    public static ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setRemainingStock(product.getRemainingStock());
        productDto.setTotalStock(product.getTotalStock());
        productDto.setSold(product.getSold());
        productDto.setPricePerPc(product.getPricePerPc());
        productDto.setSrpPerPc(product.getSrpPerPc());
        productDto.setTotalPriceRemaining(product.getTotalPriceRemaining());
        productDto.setTotalPriceSold(product.getTotalPriceSold());
        productDto.setProfit(product.getProfit());
        productDto.setExpiryDate(product.getExpiryDate());
        productDto.setGross(product.getGross());

        if(Objects.nonNull(product.getClassification())) {
            productDto.setPlainClassificationDto(PlainClassificationDto.from(product.getClassification()));
        }
        return productDto;
    }
}
