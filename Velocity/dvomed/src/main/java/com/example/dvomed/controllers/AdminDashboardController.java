package com.example.dvomed.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dvomed.dtos.DashboardStats;
import com.example.dvomed.dtos.UpdateProductRequest;
import com.example.dvomed.services.AdminDashboardServiceImpl;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardServiceImpl adminDashboardService;

    public AdminDashboardController(AdminDashboardServiceImpl adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public DashboardStats getDashboardStats() {
        
        return adminDashboardService.getDashboardStats();
    }

    @PutMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateProductDetails(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest updateProductRequest) {
        adminDashboardService.updateProductDetails(productId, updateProductRequest);
    }
    
}
