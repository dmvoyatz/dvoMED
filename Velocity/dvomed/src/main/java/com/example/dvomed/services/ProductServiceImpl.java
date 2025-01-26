package com.example.dvomed.services;

import com.example.dvomed.dtos.BestOffers;
import com.example.dvomed.dtos.ProductDiscounts;
import com.example.dvomed.dtos.ProductSales;
import com.example.dvomed.entities.Product;
import com.example.dvomed.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {
    
    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductSales> getTopSellingProducts() {
        List<Object[]> results = productRepository.findTopSellingProducts();
        return results.stream()
                .limit(5)
                .map(result -> new ProductSales(
                    ((Product) result[0]).getName(),
                    (Long) result[1]
                ))
                .collect(Collectors.toList());
    }

    public BestOffers getBestOffersProducts() {
        // Get top 3 selling products
        List<ProductSales> topSellingProducts = productRepository.findTopSellingProducts()
                .stream()
                .limit(3)
                .map(result -> new ProductSales(
                    ((Product) result[0]).getName(),
                    (Long) result[1]
                ))
                .collect(Collectors.toList());

        // Get top discounted products
        List<ProductDiscounts> topDiscountedProducts = productRepository.findTopDiscountedProducts()
                .stream()
                .limit(3)
                .map(product -> new ProductDiscounts(product.getName(), product.getPrice(), product.getDiscount()))
                .collect(Collectors.toList());

        // Get cheapest products
        List<ProductDiscounts> cheapestProducts = productRepository.findCheapestProducts()
                .stream()
                .limit(3)
                .map(product -> new ProductDiscounts(product.getName(), product.getPrice(), product.getDiscount()))
                .collect(Collectors.toList());
        

        // Create and return the DTO
        return new BestOffers(topSellingProducts, topDiscountedProducts, cheapestProducts);
    }


}
