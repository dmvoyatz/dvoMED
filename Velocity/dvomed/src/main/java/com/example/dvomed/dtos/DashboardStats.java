package com.example.dvomed.dtos;

import lombok.*;

import java.util.List;

@Data
@Builder
public class DashboardStats {
    private List<SalesStats> salesPerItem;
    private Double totalRevenue;

     // Constructor
     public DashboardStats(List<SalesStats> salesPerItem, Double totalRevenue) {
        this.salesPerItem = salesPerItem;
        this.totalRevenue = totalRevenue;
    }

    // Getters and Setters
    public List<SalesStats> getSalesPerItem() {
        return salesPerItem;
    }

    public void setSalesPerItem(List<SalesStats> salesPerItem) {
        this.salesPerItem = salesPerItem;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

}