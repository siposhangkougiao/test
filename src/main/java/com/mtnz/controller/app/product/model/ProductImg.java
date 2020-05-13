package com.mtnz.controller.app.product.model;

import javax.persistence.*;

@Table(name = "product_img")
public class ProductImg {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_img_id")
  private Long productImgId;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "img")
  private String img;

  @Column(name = "orde_by")
  private Integer ordeBy;

  public Long getProductImgId() {
    return productImgId;
  }

  public void setProductImgId(Long productImgId) {
    this.productImgId = productImgId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public Integer getOrdeBy() {
    return ordeBy;
  }

  public void setOrdeBy(Integer ordeBy) {
    this.ordeBy = ordeBy;
  }
}
