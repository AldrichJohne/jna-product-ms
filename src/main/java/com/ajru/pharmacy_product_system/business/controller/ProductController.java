package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.Product;
import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProductDto> addProduct(@RequestBody final ProductDto productDto) {
        Product product = productService.addProduct(Product.from(productDto));//productDto will be converted to product
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);//convert back to productDto
    }

    @RequestMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getProducts();//store products
        List<ProductDto> productsDto = products.stream().map(ProductDto::from).collect(Collectors.toList());//convert products to productsDto
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}" )
    public ResponseEntity<ProductDto> getProduct(@PathVariable final Long id) {
        Product product = productService.getProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable final Long id) {
        Product product = productService.deleteProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductDto> editProduct(@PathVariable final Long id, @RequestBody final ProductDto productDto) {
        Product editedProduct = productService.editProduct(id, Product.from(productDto));
        return new ResponseEntity<>(ProductDto.from(editedProduct), HttpStatus.OK);
    }

}
