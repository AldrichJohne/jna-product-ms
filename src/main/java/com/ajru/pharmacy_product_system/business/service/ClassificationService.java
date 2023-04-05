package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import com.ajru.pharmacy_product_system.business.repository.ClassificationRepository;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.commons.exception.ClassificationNotFoundException;
import com.ajru.pharmacy_product_system.business.commons.exception.ProductAlreadyAssignedException;
import com.ajru.pharmacy_product_system.business.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClassificationService {

    private final ClassificationRepository classificationRepository;
    private final ProductService productService;

    @Autowired
    public ClassificationService(ClassificationRepository classificationRepository, ProductRepository productRepository, ProductService productService) {
        this.classificationRepository = classificationRepository;
        this.productService = productService;
    }

    public Classification addClassification(Classification classification) {
        return classificationRepository.save(classification);
    }

    public List<Classification> getClassifications() {
        return StreamSupport
                .stream(classificationRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    public Classification getClassification(Long id) {
        return classificationRepository.findById(id).orElseThrow(() ->
                new ClassificationNotFoundException(id));
    }

    public Classification deleteClassification(Long id) {
        Classification classification = getClassification(id);
        classificationRepository.delete(classification);
        return classification;
    }

    @Transactional
    public Classification editClassification(Long id, Classification classification) {
        Classification classificationToEdit = getClassification(id);
        classificationToEdit.setName(classification.getName());
        return classificationToEdit;
    }

    //joint
    @Transactional
    public Classification addProductToClassification(Long classificationId, Long productId) {
        Classification classification = getClassification(classificationId);
        Product product = productService.getProduct(productId);
        if(Objects.nonNull(product.getClassification())){
            throw new ProductAlreadyAssignedException(productId,
                    product.getClassification().getId());
        }
        classification.addProduct(product);
        product.setClassification(classification);
        return classification;
    }

    //add product then direct to a classification
    @Transactional
    public Classification addProductToClassificationDirectly(Long classificationId, Product product) {
        Product productToAdd;
        product.setSold(0);
        product.setProfit(0);
        product.setTotalPriceSold(0);
        productToAdd = product;
        productToAdd.setRemainingStock(product.getTotalStock() - product.getSold());
        productToAdd.setTotalPriceRemaining(productToAdd.getRemainingStock() * product.getPricePerPc());

        Classification classification = getClassification(classificationId);
        classification.addProduct(productToAdd);
        product.setClassification(classification);
        return classification;
    }

    @Transactional
    public Classification removeProductFromClassification(Long classificationId, Long productId) {
        Classification classification = getClassification(classificationId);
        Product product = productService.getProduct(productId);
        classification.removeProduct(product);
        product.setClassification(null);
        return classification;
    }
}
