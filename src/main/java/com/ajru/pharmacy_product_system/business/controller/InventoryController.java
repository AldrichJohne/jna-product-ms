package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.model.dto.ClassificationDto;
import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.service.ClassificationService;
import com.ajru.pharmacy_product_system.business.service.ProductService;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-ms/inventory")
public class InventoryController {

    private final ClassificationService classificationService;
    private final ProductService productService;
    private final Logger logger;

    @Autowired
    public InventoryController(ClassificationService classificationService, ProductService productService) {
        this.classificationService = classificationService;
        this.productService = productService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //for classification

    //add classification
    @PostMapping("/classifications")
    public ResponseEntity<ClassificationDto> addClassification(
            @RequestBody final ClassificationDto classificationDto,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "add product classification/category",
                request.getMethod(),
                request.getRequestURL());
        final Classification classification = classificationService.addClassification(Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //find all classifications
    @GetMapping("/classifications")
    public ResponseEntity<List<ClassificationDto>> getClassifications(
            final HttpServletRequest request
    ) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "get product classifications/category",
                request.getMethod(),
                request.getRequestURL());
        final List<Classification> classifications = classificationService.getClassifications();
        final List<ClassificationDto> classificationsDto = classifications.stream().map(ClassificationDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(classificationsDto, HttpStatus.OK);
    }

    //find a classification
    @GetMapping(value = "/classifications/{id}")
    public ResponseEntity<ClassificationDto> getClassification(
            @PathVariable final Long id,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "get specific product classification/category",
                request.getMethod(),
                request.getRequestURL());
        final Classification classification = classificationService.getClassification(id);
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //edit a classification
    @PutMapping("/classifications/{id}")
    public ResponseEntity<ClassificationDto> getClassification(
            @PathVariable final Long id,
            @RequestBody final ClassificationDto classificationDto,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "update a product classification/category",
                request.getMethod(),
                request.getRequestURL());
        final Classification classification = classificationService.editClassification(id, Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //for product

    //add product with a classification
    @PostMapping("{classificationId}/products")
    public ResponseEntity<ClassificationDto> addProductToClassificationDirectly(
            @PathVariable final Long classificationId,
            @RequestBody final ProductDto productDto,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "add single product",
                request.getMethod(),
                request.getRequestURL());
        final Classification classification = classificationService.addProductToClassificationDirectly(classificationId, Product.from(productDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //delete a product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDto> deleteProduct(
            @PathVariable final Long id,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "delete a single product",
                request.getMethod(),
                request.getRequestURL());
        final Product product = productService.deleteProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    //edit a product
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> editProduct(
            @PathVariable final Long id,
            @RequestBody final ProductDto productDto,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "update a product",
                request.getMethod(),
                request.getRequestURL());
        final Product editedProduct = productService.editProduct(id, Product.from(productDto));
        return new ResponseEntity<>(ProductDto.from(editedProduct), HttpStatus.OK);
    }

    //find a product
    @GetMapping("/products/{id}" )
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable final Long id,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "fetch a single product",
                request.getMethod(),
                request.getRequestURL());
        final Product product = productService.getProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    //find all products
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(
            final HttpServletRequest request
    ) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "fetch all products",
                request.getMethod(),
                request.getRequestURL());
        final List<Product> products = productService.getProducts();//store products
        final List<ProductDto> productsDto = products.stream().map(ProductDto::from).collect(Collectors.toList());//convert products to productsDto
        for (ProductDto productDto : productsDto) {
            productDto.setClassName(productDto.getPlainClassificationDto().getName());
        }
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

}
