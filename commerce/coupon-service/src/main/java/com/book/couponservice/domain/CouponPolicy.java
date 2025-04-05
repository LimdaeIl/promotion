package com.book.couponservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "coupon_policies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponPolicy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DiscountType discountType;

  @Column(nullable = false)
  private Integer discountValue;

  @Column(nullable = false)
  private Integer minimumOrderAmount;

  @Column(nullable = false)
  private Integer maximumDiscountAmount;


  @Column(nullable = false)
  private Integer totalQuantity;


  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column(nullable = false)
  @Setter
  private LocalDateTime endTime;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public enum DiscountType {
    FIXED_AMOUNT, // 정액 할인
    PERCENTAGE // 정률 할인
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
