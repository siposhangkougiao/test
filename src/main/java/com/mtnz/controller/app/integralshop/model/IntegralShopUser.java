package com.mtnz.controller.app.integralshop.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "integral_shop_user")
public class IntegralShopUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "integral")
  private BigDecimal integral;

  @Transient
  private List<IntegralShopDetail> list = new ArrayList<>();

  public List<IntegralShopDetail> getList() {
    return list;
  }

  public void setList(List<IntegralShopDetail> list) {
    this.list = list;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public BigDecimal getIntegral() {
    return integral;
  }

  public void setIntegral(BigDecimal integral) {
    this.integral = integral;
  }
}
