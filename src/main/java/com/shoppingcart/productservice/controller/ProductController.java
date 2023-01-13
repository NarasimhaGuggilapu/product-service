package com.shoppingcart.productservice.controller;


import com.shoppingcart.productservice.dto.Product;
import com.shoppingcart.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/v1")
public class ProductController {

    private ProductService productService;

    public ProductController( ProductService productService){
        this.productService = productService;
    }
    @PostMapping("/addProduct")
    ResponseEntity<Product> addProduct(@RequestBody @Valid Product product){

        String status = productService.addProduct(product);
        log.info("Product Added Status  - {}", status );
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/productList")
    List<Product> productList() {
        log.info("Listing Products");
        List<Product> products = productService.listAllProducts();
        log.info("all Products Returned - {}" , products);
        return products;
    }

    @GetMapping("/productList/{category}")
    List<Product> productCategoryList(@PathVariable String category){
        return productService.productCategoryList(category);
    }

    @GetMapping("/product/{id}")
    Product productById(@PathVariable String id){
        return productService.productById(id);
    }

    @PutMapping("/productUpdate")
    String  updateProduct(@RequestBody Product product){

        return productService.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    String deleteProductById(@PathVariable String id){
        return productService.deleteProductById(id);
    }

}
