package com.example.dvomed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`orders`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private BigDecimal totalPrice;


    @Column(name = "order_date", updatable = false, insertable = false)
    private Timestamp orderDate;

    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;

}