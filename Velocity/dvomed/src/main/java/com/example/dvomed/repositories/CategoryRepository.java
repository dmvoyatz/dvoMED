package com.example.dvomed.repositories;

import com.example.dvomed.entities.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Method to find Category by its ID (if not already available)
    Optional<Category> findById(Long id);
}
