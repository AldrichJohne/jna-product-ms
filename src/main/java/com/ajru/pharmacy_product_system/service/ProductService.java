package com.ajru.pharmacy_product_system.service;

import com.ajru.pharmacy_product_system.model.Product;
import com.ajru.pharmacy_product_system.model.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.reposiroty.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        Product productToAdd = new Product();
        product.setSold(0);
        product.setProfit(0);
        product.setTotalPriceSold(0);
        productToAdd = product;
        productToAdd.setRemainingStock(product.getTotalStock() - product.getSold());
        productToAdd.setTotalPriceRemaining(productToAdd.getRemainingStock() * product.getPricePerPc());
        return productRepository.save(productToAdd);
    }

    public List<Product> getProducts() {
        return StreamSupport
                    .stream(productRepository.findAll().spliterator(),false)
                    .collect(Collectors.toList());
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
        Product productToEditFinal= new Product();
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
