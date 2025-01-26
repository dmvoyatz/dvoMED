package com.example.dvomed.dtos;

import java.util.List;

public class BestOffers {
    private List<ProductSales> topSellingProducts;
    private List<ProductDiscounts> topDiscountedProducts;
    private List<ProductDiscounts> cheapestProducts;

    // Constructor
    public BestOffers(List<ProductSales> topSellingProducts, List<ProductDiscounts> topDiscountedProducts, List<ProductDiscounts> cheapestProducts) {
        this.topSellingProducts = topSellingProducts;
        this.topDiscountedProducts = topDiscountedProducts;
        this.cheapestProducts = cheapestProducts;
    }

    // Getters and Setters
    public List<ProductSales> getTopSellingProducts() {
        return topSellingProducts;
    }

    public void setTopSellingProducts(List<ProductSales> topSellingProducts) {
        this.topSellingProducts = topSellingProducts;
    }

    public List<ProductDiscounts> getTopDiscountedProducts() {
        return topDiscountedProducts;
    }

    public void setTopDiscountedProducts(List<ProductDiscounts> topDiscountedProducts) {
        this.topDiscountedProducts = topDiscountedProducts;
    }

    public List<ProductDiscounts> getCheapestProducts() {
        return cheapestProducts;
    }

    public void setCheapestProducts(List<ProductDiscounts> cheapestProducts) {
        this.cheapestProducts = cheapestProducts;
    }
}

