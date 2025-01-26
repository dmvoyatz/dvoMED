package com.example.dvomed.dtos;

import java.util.List;

public class PublicDashboardOverview {
    private int totalAvailableProducts;
    private List<ProductInfo> availableProducts;
    private List<ProductSales> topSellingProducts;

    public PublicDashboardOverview(int totalAvailableProducts, List<ProductInfo> availableProducts, List<ProductSales> topSellingProducts) {
        this.totalAvailableProducts = totalAvailableProducts;
        this.availableProducts = availableProducts;
        this.topSellingProducts = topSellingProducts;
    }

    public int getTotalAvailableProducts() {
        return totalAvailableProducts;
    }

    public List<ProductInfo> getAvailableProducts() {
        return availableProducts;
    }

    public List<ProductSales> getTopSellingProducts() {
        return topSellingProducts;
    }
}