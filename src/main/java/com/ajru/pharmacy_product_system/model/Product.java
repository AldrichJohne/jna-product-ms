package com.ajru.pharmacy_product_system.model;

import com.ajru.pharmacy_product_system.model.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Product")
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "product_name", nullable = false, unique = true)
    private String name;

    @Column(name = "remaining_stock")
    private long remainingStock;

    @Column(name = "total_stock")
    private long totalStock;

    @Column(name = "sold")
    private long sold;

    @Column(name = "price_per_piece")
    private double pricePerPc;

    @Column(name = "srp_per_piece")
    private double srpPerPc;

    @Column(name = "total_price_remaining")
    private double totalPriceRemaining;

    @Column(name = "total_price_sold")
    private double totalPriceSold;

    @Column(name = "profit")
    private double profit;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToOne
    private Classification classification;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "product_id")
    private List<ProductTrxnJournal> productTrxnJournals = new ArrayList<>();

    public void addProductTrxnJournal(ProductTrxnJournal productTrxnJournal) {
        productTrxnJournals.add(productTrxnJournal);
    }

    //transform ProductDto to product
    public static Product from(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setRemainingStock(productDto.getRemainingStock());
        product.setTotalStock(productDto.getTotalStock());
        product.setSold(productDto.getSold());
        product.setPricePerPc(productDto.getPricePerPc());
        product.setSrpPerPc(productDto.getSrpPerPc());
        product.setTotalPriceRemaining(productDto.getTotalPriceRemaining());
        product.setTotalPriceSold(productDto.getTotalPriceSold());
        product.setProfit(productDto.getProfit());
        product.setExpiryDate(productDto.getExpiryDate());
        return product;
    }


}
