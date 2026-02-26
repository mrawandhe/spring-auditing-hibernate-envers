package com.example.controller;

import com.example.entity.OrderEntity;
import com.example.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @PostMapping
  public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order) {
    OrderEntity savedOrder = orderRepository.save(order);
    return ResponseEntity.ok(savedOrder);
  }

  @GetMapping
  public ResponseEntity<List<OrderEntity>> getAllOrders() {
    List<OrderEntity> orders = orderRepository.findAll();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
    Optional<OrderEntity> order = orderRepository.findById(id);
    return order.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id,
                                                 @RequestBody OrderEntity updatedOrder) {
    return orderRepository.findById(id)
        .map(existingOrder -> {
          existingOrder.setOrderNumber(updatedOrder.getOrderNumber());
          existingOrder.setOrderDate(updatedOrder.getOrderDate());
          existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
          existingOrder.setStatus(updatedOrder.getStatus());
          OrderEntity savedOrder = orderRepository.save(existingOrder);
          return ResponseEntity.ok(savedOrder);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    return orderRepository.findById(id)
        .map(order -> {
          orderRepository.delete(order);
          return ResponseEntity.noContent().<Void>build();
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}