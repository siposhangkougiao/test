package com.mtnz.controller.app.store.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "store_lose")
public class StoreLose {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "status")
  private Integer status;

  @Column(name = "creat_time")
  private Date creatTime;

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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }
}
