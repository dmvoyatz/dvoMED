package com.example.dvomed.repositories;

import com.example.dvomed.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p, SUM(od.quantity) as totalQuantity " +
           "FROM Product p " +
           "JOIN OrderDetails od ON p.id = od.product.id " +
           "JOIN Order o ON od.order.id = o.id " +
           "WHERE o.orderStatus.id in (1,2,3) " +
           "GROUP BY p.id " +
           "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts();

    @Query("SELECT p FROM Product p ORDER BY p.discount DESC")
    List<Product> findTopDiscountedProducts();

    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> findCheapestProducts();

    @Query("SELECT p, SUM(od.quantity) as totalQuantity, SUM(od.price) as sales " +
           "FROM Product p " +
           "JOIN OrderDetails od ON p.id = od.product.id " +
           "JOIN Order o ON od.order.id = o.id " +
           "WHERE o.orderStatus.id in (1,2,3) " +
           "GROUP BY p.id " )
    List<Object[]> getSalesPerItem();

    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Double getTotalRevenue();

}
