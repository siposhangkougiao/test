package com.mtnz.controller.app.mysql.model;


import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long productId;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "norms1")
  private String norms1;

  @Column(name = "norms2")
  private String norms2;

  @Column(name = "norms3")
  private String norms3;

  @Column(name = "product_price")
  private String productPrice;

  @Column(name = "purchase_price")
  private String purchasePrice;

  @Column(name = "production_enterprise")
  private String productionEnterprise;

  @Column(name = "product_img")
  private String productImg;

  @Column(name = "date")
  private String date;

  @Column(name = "status")
  private Integer status;

  @Column(name = "kucun")
  private Integer kucun;

  @Column(name = "likucun")
  private BigDecimal likucun;

  @Column(name = "type")
  private String type;

  @Column(name = "bar_code")
  private String barCode;

  @Column(name = "bar_code_number")
  private String barCodeNumber;

  @Column(name = "supplier_id")
  private Long supplierId;

  @Column(name = "number")
  private String number;

  @Column(name = "url")
  private String url;

  @Column(name = "number_tow")
  private String numberTow;

  @Column(name = "type2")
  private String type2;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
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

  public String getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(String productPrice) {
    this.productPrice = productPrice;
  }

  public String getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(String purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public String getProductionEnterprise() {
    return productionEnterprise;
  }

  public void setProductionEnterprise(String productionEnterprise) {
    this.productionEnterprise = productionEnterprise;
  }

  public String getProductImg() {
    return productImg;
  }

  public void setProductImg(String productImg) {
    this.productImg = productImg;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getKucun() {
    return kucun;
  }

  public void setKucun(Integer kucun) {
    this.kucun = kucun;
  }

  public BigDecimal getLikucun() {
    return likucun;
  }

  public void setLikucun(BigDecimal likucun) {
    this.likucun = likucun;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBarCode() {
    return barCode;
  }

  public void setBarCode(String barCode) {
    this.barCode = barCode;
  }

  public String getBarCodeNumber() {
    return barCodeNumber;
  }

  public void setBarCodeNumber(String barCodeNumber) {
    this.barCodeNumber = barCodeNumber;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getNumberTow() {
    return numberTow;
  }

  public void setNumberTow(String numberTow) {
    this.numberTow = numberTow;
  }

  public String getType2() {
    return type2;
  }

  public void setType2(String type2) {
    this.type2 = type2;
  }
}
