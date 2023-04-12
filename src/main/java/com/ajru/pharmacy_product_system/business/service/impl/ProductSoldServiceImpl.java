package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import com.ajru.pharmacy_product_system.business.service.ProductService;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSoldServiceImpl implements ProductSoldService {

    private final ProductSoldRepository productSoldRepository;
    private final ProductService productService;
    private final double discountRate;

    public ProductSoldServiceImpl(
            ProductSoldRepository productSoldRepository,
            ProductService productService,
            @Value("${discount.rate}") double discountRate) {
        this.productSoldRepository = productSoldRepository;
        this.productService = productService;
        this.discountRate = discountRate;
    }

    @Override
    public ProductSold sellProduct(Long id, ProductSold productSold, Boolean isDiscounted) {

        productService.getProduct(id);

        double amtSrp = this.getAmountSrp(productSold.getSrp(), productSold.getSoldQuantity(), isDiscounted);

        double amtPrc = this.getAmountPrice(productSold.getPrice(), productSold.getSoldQuantity());

        double profit = this.getProfit(amtSrp, amtPrc);

        ProductSold productSoldFinal;
        productSoldFinal = productSold;
        productSoldFinal.setAmount(amtSrp);
        productSoldFinal.setProfit(profit);
        productSoldFinal.setIsDiscounted(String.valueOf(isDiscounted));

        updateSoldProductOrigin(id, productSold);

        return productSoldRepository.save(productSoldFinal);
    }

    public List<ProductSold> getProductSold() {
        return new ArrayList<>(productSoldRepository.findAll());
    }

    private void updateSoldProductOrigin(Long id, ProductSold productSold) {
        Product productToUpdate = productService.getProduct(id);

        productToUpdate.setRemainingStock(
                (productToUpdate.getRemainingStock() - productSold.getSoldQuantity())
        );
        productToUpdate.setSold(
                (productToUpdate.getSold() + productSold.getSoldQuantity())
        );
        productToUpdate.setTotalPriceRemaining(
                (productToUpdate.getRemainingStock() * productToUpdate.getPricePerPc())
        );
        productToUpdate.setTotalPriceSold(
                (productToUpdate.getSold() * productToUpdate.getPricePerPc())
        );
        productToUpdate.setProfit(
                (productToUpdate.getSold() * productToUpdate.getSrpPerPc())
                        - (productToUpdate.getSold() * productToUpdate.getPricePerPc())
        );

    }

    private double getAmountSrp(double srp, int soldQuantity, Boolean isDiscounted) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double finalSrp;

        if (isDiscounted.equals(true)) {
            String finalSrpStr = decimalFormat.format((srp * soldQuantity) * this.discountRate);
            finalSrp = Double.parseDouble(finalSrpStr);
        } else {
            finalSrp = (srp* soldQuantity);
        }

        return finalSrp;
    }

    private double getAmountPrice(double price, int soldQuantity) {
        return price * soldQuantity;
    }

    private double getProfit(double amountSrp, double amountPrice) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String profitStr = decimalFormat.format(amountSrp - amountPrice);
        return Double.parseDouble(profitStr);
    }

}
