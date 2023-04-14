package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.model.dto.ClassificationDto;
import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.service.ClassificationService;
import com.ajru.pharmacy_product_system.business.service.ProductService;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final ClassificationService classificationService;
    private final ProductService productService;
    private final ProductSoldService productSoldService;

    @Autowired
    public InventoryController(ClassificationService classificationService, ProductService productService, ProductSoldService productSoldService) {
        this.classificationService = classificationService;
        this.productService = productService;
        this.productSoldService = productSoldService;
    }

    //for classification

    //add classification
    @PostMapping("/classifications")
    public ResponseEntity<ClassificationDto> addClassification(@RequestBody final ClassificationDto classificationDto) {
        Classification classification = classificationService.addClassification(Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //find all classifications
    @GetMapping("/classifications")
    public ResponseEntity<List<ClassificationDto>> getClassifications() {
        List<Classification> classifications = classificationService.getClassifications();
        List<ClassificationDto> classificationsDto = classifications.stream().map(ClassificationDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(classificationsDto, HttpStatus.OK);
    }

    //find a classification
    @GetMapping(value = "/classifications/{id}")
    public ResponseEntity<ClassificationDto> getClassification(@PathVariable final Long id) {
        Classification classification = classificationService.getClassification(id);
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //edit a classification
    @PutMapping("/classifications/{id}")
    public ResponseEntity<ClassificationDto> getClassification(@PathVariable final Long id, @RequestBody final ClassificationDto classificationDto) {
        Classification classification = classificationService.editClassification(id, Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //for product

    //add product with a classification
    @PostMapping("{classificationId}/products")
    public ResponseEntity<ClassificationDto> addProductToClassificationDirectly(@PathVariable final Long classificationId,
                                                                                @RequestBody final ProductDto productDto) {
        Classification classification = classificationService.addProductToClassificationDirectly(classificationId, Product.from(productDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //delete a product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable final Long id) {
        Product product = productService.deleteProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    //edit a product
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> editProduct(@PathVariable final Long id, @RequestBody final ProductDto productDto) {
        Product editedProduct = productService.editProduct(id, Product.from(productDto));
        return new ResponseEntity<>(ProductDto.from(editedProduct), HttpStatus.OK);
    }

    //sell a product (total stock - sold quantity)
    @PutMapping("/productToSell/{id}")
    public ResponseEntity<ProductDto> sellProduct(@PathVariable final Long id, @RequestBody final ProductDto productDto) {
        Product editedProduct = productService.editProduct(id, Product.from(productDto));
        return new ResponseEntity<>(ProductDto.from(editedProduct), HttpStatus.OK);
    }

    //find a product
    @GetMapping("/products/{id}" )
    public ResponseEntity<ProductDto> getProduct(@PathVariable final Long id) {
        Product product = productService.getProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    //find all products
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getProducts();//store products
        List<ProductDto> productsDto = products.stream().map(ProductDto::from).collect(Collectors.toList());//convert products to productsDto
        for (int index = 0; index < productsDto.size(); index ++) {
            productsDto.get(index).setClassName(productsDto.get(index).getPlainClassificationDto().getName());
        }
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

}
