package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.commons.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductRepository;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSoldRepository productSoldRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductSoldRepository productSoldRepository) {
        this.productRepository = productRepository;
        this.productSoldRepository = productSoldRepository;
    }

    public Product addProduct(Product product) {
        Product productToAdd;
        product.setSold(0);
        product.setProfit(0);
        product.setTotalPriceSold(0);
        productToAdd = product;
        productToAdd.setRemainingStock(product.getTotalStock() - product.getSold());
        productToAdd.setTotalPriceRemaining(productToAdd.getRemainingStock() * product.getPricePerPc());
        return productRepository.save(productToAdd);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(productRepository.findAll());
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
        new ProductNotFoundException(id));
    }

    public Product deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
        return product;
    }

    @Transactional
    public Product editProduct(Long id, Product product) {
        Product productToEditFinal;
        Product productToEdit = getProduct(id);
        productToEdit.setName(product.getName());
        productToEdit.setTotalStock(product.getTotalStock());
        productToEdit.setExpiryDate(product.getExpiryDate());
        productToEditFinal = productToEdit;
        productToEditFinal.setRemainingStock(productToEdit.getTotalStock() - productToEdit.getSold());
        productToEditFinal.setTotalPriceRemaining(productToEdit.getRemainingStock() * productToEdit.getPricePerPc());
        productToEditFinal.setTotalPriceSold(productToEdit.getSold() * productToEdit.getPricePerPc());
        productToEditFinal.setProfit((productToEdit.getSrpPerPc() - productToEdit.getPricePerPc()) * productToEdit.getSold());

        return productToEditFinal;
    }

    @Transactional
    public void updateProductOnCashierActivity(Long productId) {
        List<ProductSold> productSoldList = productSoldRepository.findByProductId(productId);
        Product productParentToUpdate = getProduct(productId);
        int totalQuantity = 0;
        double totalProfit = 0.00;
        double totalGrossAmount = 0.00;
        for (ProductSold sold : productSoldList) {
            totalQuantity = totalQuantity + sold.getSoldQuantity();
            totalProfit = totalProfit + sold.getProfit();
            totalGrossAmount = totalGrossAmount + sold.getAmount();
        }

        productParentToUpdate.setGross(String.valueOf(totalGrossAmount));
        productParentToUpdate.setSold(totalQuantity);
        productParentToUpdate.setProfit(totalProfit);
        productParentToUpdate.setRemainingStock(
                productParentToUpdate.getTotalStock() - totalQuantity
        );
        productParentToUpdate.setTotalPriceRemaining(
                productParentToUpdate.getRemainingStock() * productParentToUpdate.getPricePerPc()
        );
        productParentToUpdate.setTotalPriceSold(
               totalQuantity * productParentToUpdate.getPricePerPc()
        );
    }

}
