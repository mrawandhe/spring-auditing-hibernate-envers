package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderEntity extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderNumber;

  private LocalDateTime orderDate;

  private Double totalAmount;

  private String status;

}