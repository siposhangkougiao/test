package com.mtnz.controller.app.supplierbalance.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "supplier_balance_order")
public class SupplierBalanceOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name ="order_id")
  private Long orderId;

  @Column(name ="price")
  private BigDecimal price;

  @Column(name ="is_back")
  private Integer isBack;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "supplier_id")
  private Long supplierId;

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getIsBack() {
    return isBack;
  }

  public void setIsBack(Integer isBack) {
    this.isBack = isBack;
  }
}
