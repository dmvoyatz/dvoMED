package com.example.dvomed.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dvomed.dtos.DashboardStats;
import com.example.dvomed.dtos.SalesStats;
import com.example.dvomed.dtos.UpdateProductRequest;
import com.example.dvomed.entities.Product;
import com.example.dvomed.repositories.ProductRepository;
import com.example.dvomed.exceptions.ProductNotFoundException;

@Service
public class AdminDashboardServiceImpl {

    private final ProductRepository itemRepository;

    public AdminDashboardServiceImpl(ProductRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public DashboardStats getDashboardStats() {
        // Fetch statistics from the repository
        List<SalesStats> salesPerItem = itemRepository.getSalesPerItem()
                .stream()
                .map(record -> SalesStats.builder()
                    .name(((Product) record[0]).getName())
                    .salesCount((Long) record[1])
                    .revenue(((BigDecimal) record[2]).doubleValue())
                    .build())
            .collect(Collectors.toList());

        Double totalRevenue = itemRepository.getTotalRevenue();

        // Map the data to DTO
        return new DashboardStats(salesPerItem,totalRevenue);
    }

    public void updateProductDetails(Long productId, UpdateProductRequest updateProductRequest) {
        Product product = itemRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        // Update product fields based on the request
        if (updateProductRequest.getPrice() != null) {
            product.setPrice(updateProductRequest.getPrice());
        }
        if (updateProductRequest.getDescription() != null) {
            product.setDescription(updateProductRequest.getDescription());
        }
        if (updateProductRequest.getDiscount() != null) {
            product.setDiscount(updateProductRequest.getDiscount());
        }
        if (updateProductRequest.getQuantity() != null) {
            product.setQuantity(updateProductRequest.getQuantity());
        }

        itemRepository.save(product);
    }

}