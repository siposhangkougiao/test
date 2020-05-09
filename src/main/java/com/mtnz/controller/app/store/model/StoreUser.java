package com.mtnz.controller.app.store.model;


import javax.persistence.*;

@Table(name = "store_user")
public class StoreUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "status")
  private Integer status;

  @Column(name = "ismr")
  private Integer ismr;


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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getIsmr() {
    return ismr;
  }

  public void setIsmr(Integer ismr) {
    this.ismr = ismr;
  }
}
