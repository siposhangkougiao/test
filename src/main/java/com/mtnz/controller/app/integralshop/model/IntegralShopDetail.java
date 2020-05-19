package com.mtnz.controller.app.integralshop.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "integral_shop_detail")
public class IntegralShopDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "integral")
  private BigDecimal integral;

  @Column(name = "creat_time")
  private Date creatTime;

  @Column(name = "today")
  private String today;

  @Column(name = "type")
  private Integer type;

  @Transient
  private Integer pageNumber=1;

  @Transient
  private Integer pageSize=10;

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public BigDecimal getIntegral() {
    return integral;
  }

  public void setIntegral(BigDecimal integral) {
    this.integral = integral;
  }

  public Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }

  public String getToday() {
    return today;
  }

  public void setToday(String today) {
    this.today = today;
  }
}
