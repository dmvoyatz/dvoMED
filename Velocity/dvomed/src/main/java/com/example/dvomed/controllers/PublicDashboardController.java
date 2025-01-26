package com.example.dvomed.controllers;

import com.example.dvomed.dtos.BestOffers;
import com.example.dvomed.dtos.ProductInfo;
import com.example.dvomed.dtos.ProductSales;
import com.example.dvomed.dtos.PublicDashboardOverview;
import com.example.dvomed.entities.Category;
import com.example.dvomed.entities.Product;
import com.example.dvomed.repositories.CategoryRepository;
import com.example.dvomed.services.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class PublicDashboardController {

    private final ProductServiceImpl productService;
    private final CategoryRepository categoryRepository;

    public PublicDashboardController(ProductServiceImpl productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/overview")
    public PublicDashboardOverview getPublicDashboardOverview() {
        List<Product> allProducts = productService.getAllProducts();
        int totalAvailableProducts = (int) allProducts.stream().filter(product -> product.getQuantity() > 0).count();
        List<ProductInfo> availableProducts = getAvailableProducts(allProducts);
        List<ProductSales> topSellingProducts = getTopSellingProducts(allProducts);

        return new PublicDashboardOverview(totalAvailableProducts, availableProducts, topSellingProducts);
    }

    private List<ProductInfo> getAvailableProducts(List<Product> allProducts) {
        return allProducts.stream()
                .filter(product -> product.getQuantity() > 0)
                .map(product -> {
                    Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
                    String categoryLabel = category != null ? category.getLabel() : "Unknown";
                    return new ProductInfo(
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            categoryLabel);
                })
                .collect(Collectors.toList());
        
    }

    private List<ProductSales> getTopSellingProducts(List<Product> allProducts) {
        // return allProducts.subList(0, Math.min(5, allProducts.size()));
        return productService.getTopSellingProducts();
    }

    @GetMapping("/best-offers")
    public BestOffers getBestOffers() {
        return productService.getBestOffersProducts();
    }
    
    
}
