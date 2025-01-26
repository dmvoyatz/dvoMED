package com.example.dvomed.dtos;

import lombok.*;

@Data
@Builder
public class SalesStats {
    private String name;
    private Long salesCount;
    private Double revenue;
}
