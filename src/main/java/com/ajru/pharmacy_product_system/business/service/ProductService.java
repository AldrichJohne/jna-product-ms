package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.commons.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.business.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
        productToEdit.setPricePerPc(product.getPricePerPc());
        productToEdit.setSrpPerPc(product.getSrpPerPc());
        productToEdit.setExpiryDate(product.getExpiryDate());
        productToEdit.setSold(product.getSold());
        productToEditFinal = productToEdit;
        productToEditFinal.setRemainingStock(productToEdit.getTotalStock() - product.getSold());
        productToEditFinal.setTotalPriceRemaining(productToEdit.getRemainingStock() * productToEdit.getPricePerPc());
        productToEditFinal.setTotalPriceSold(productToEdit.getSold() * productToEdit.getPricePerPc());
        productToEditFinal.setProfit((productToEdit.getSrpPerPc() - productToEdit.getPricePerPc()) * productToEdit.getSold());

        return productToEditFinal;
    }

}
