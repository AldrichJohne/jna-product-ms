package com.ajru.pharmacy_product_system.business.model.entity;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ProductSold")
@Table(name = "product_sold")
public class ProductSold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "classification")
    private String classification;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "srp")
    private double srp;

    @Column(name = "quantity")
    private int soldQuantity;

    @Column(name = "amount")
    private double amount;

    @Column(name = "profit")
    private double profit;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "isDiscounted")
    private String isDiscounted;

    @Column(name = "pharmacist")
    private String pharmacist;

    //transform ProductSoldDto to ProductSold
    public static ProductSold from(ProductSoldDto productSoldDto) {
        ProductSold productSold = new ProductSold();
        productSold.setClassification(productSoldDto.getClassification());
        productSold.setProductName(productSoldDto.getProductName());
        productSold.setPrice(productSoldDto.getPrice());
        productSold.setSrp(productSoldDto.getSrp());
        productSold.setSoldQuantity(productSoldDto.getSoldQuantity());
        productSold.setAmount(productSoldDto.getAmount());
        productSold.setProfit(productSoldDto.getProfit());
        productSold.setTransactionDate(productSoldDto.getTransactionDate());
        productSold.setIsDiscounted(productSoldDto.getIsDiscounted());
        productSold.setProductId(productSoldDto.getProductId());
        productSold.setPharmacist(productSoldDto.getPharmacist());

        return productSold;
    }
}
