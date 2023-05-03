package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.repository.ProductRepository;
import com.ajru.pharmacy_product_system.business.service.ClassificationService;
import com.ajru.pharmacy_product_system.business.service.ProductServiceV2;
import com.ajru.pharmacy_product_system.commons.exception.ClassificationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceV2Impl implements ProductServiceV2 {

    private final ProductRepository productRepository;
    private final ClassificationService classificationService;

    public ProductServiceV2Impl(final ProductRepository productRepository,
                                final ClassificationService classificationService) {
        this.productRepository = productRepository;
        this.classificationService = classificationService;
    }

    @Override
    public List<Product> setUpProducts(Long classId, List<ProductDto> productDtoList) {
        try {
            final List<Product> products = new ArrayList<>();
            final Classification classification = classificationService.getClassification(classId);

            for (ProductDto dto : productDtoList) {
                Product product = Product.from(dto);
                products.add(product);
            }

            for (Product product : products) {
                product.setGross("0");
                product.setSold(0);
                product.setProfit(0);
                product.setTotalPriceSold(0);
                product.setClassification(classification);
                product.setRemainingStock(product.getTotalStock() - product.getSold());
                product.setTotalPriceRemaining(product.getRemainingStock() * product.getPricePerPc());
            }

            this.batchSaveProducts(products);

            return products;
        } catch (Exception err) {
            throw new ClassificationNotFoundException(classId);
        }
    }

    private void batchSaveProducts(final List<Product> products) {
        productRepository.saveAll(products);
    }
}
