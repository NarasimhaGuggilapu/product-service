package com.shoppingcart.productservice.service;

import com.shoppingcart.productservice.exception.CurrencyNotValidException;
import com.shoppingcart.productservice.repository.ProductRepository;
import com.shoppingcart.productservice.dto.Product;
import com.shoppingcart.productservice.exception.OfferNotValidException;
import com.shoppingcart.productservice.service.config.ProductServiceConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductServiceConfiguration productServiceConfiguration;

    public String addProduct(Product product) {

        log.info( "Adding Product");
        if(product.getPrice() == 0 && product.getDiscount() > 0) {
            throw new OfferNotValidException("No Discount is Allowed at 0 product Price");
        }
        if(!productServiceConfiguration.getCurrencies().contains(product.getCurrency().toUpperCase())){
            throw new CurrencyNotValidException("Invalid Currency. Valid Currencies - " + productServiceConfiguration.getCurrencies());
        }
        productRepository.save(product);

        return "Success";

    }

    public List<Product> listAllProducts() {

        return productRepository.findAll();
    }

    public List<Product> productCategoryList(String category) {

        return productRepository.findByCategory(category);
    }


    public Product productById(String id) {
        return productRepository.findById(id).get();
    }

    public String updateProduct(Product product) {
        productRepository.save(product);
        return "Product Updated";
    }

    public String deleteProductById(String id) {
        productRepository.deleteById(id);
        return "Product Deleted";
    }
}
