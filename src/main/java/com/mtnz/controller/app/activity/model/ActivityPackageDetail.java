package com.mtnz.controller.app.activity.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "activity_package_detail")
public class ActivityPackageDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "package_id")
  private Long packageId;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "product_price")
  private BigDecimal productPrice;

  @Column(name = "purchase_price")
  private BigDecimal purchasePrice;

  @Column(name = "norms1")
  private String norms1;

  @Column(name = "norms2")
  private String norms2;

  @Column(name = "norms3")
  private String norms3;

  @Column(name = "whole_sale")
  private BigDecimal wholeSale;

  @Column(name = "retail_sale")
  private BigDecimal retailSale;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "user_id")
  private Long userId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPackageId() {
    return packageId;
  }

  public void setPackageId(Long packageId) {
    this.packageId = packageId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public String getNorms1() {
    return norms1;
  }

  public void setNorms1(String norms1) {
    this.norms1 = norms1;
  }

  public String getNorms2() {
    return norms2;
  }

  public void setNorms2(String norms2) {
    this.norms2 = norms2;
  }

  public String getNorms3() {
    return norms3;
  }

  public void setNorms3(String norms3) {
    this.norms3 = norms3;
  }

  public BigDecimal getWholeSale() {
    return wholeSale;
  }

  public void setWholeSale(BigDecimal wholeSale) {
    this.wholeSale = wholeSale;
  }

  public BigDecimal getRetailSale() {
    return retailSale;
  }

  public void setRetailSale(BigDecimal retailSale) {
    this.retailSale = retailSale;
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
}
