package com.example.dvomed.dtos;

import java.math.BigDecimal;

public class ProductInfo {
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryLabel;

    public ProductInfo(String name, String description, BigDecimal price, String categoryLabel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryLabel = categoryLabel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }
}
