package com.mtnz.controller.app.activity.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "activity_package")
public class ActivityPackage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "total_money")
  private BigDecimal totalMoney;

  @Column(name = "money")
  private BigDecimal money;

  @Column(name = "discount_money")
  private BigDecimal discountMoney;

  @Column(name = "creat_time")
  private Date creatTime;

  @Column(name = "isDelete")
  private Integer isDelete;

  @Transient
  private ActivityPackageDetail activityPackageDetail;

  @Transient
  private List<ActivityPackageDetail> list;

  @Transient
  private Integer pageNumber;

  @Transient
  private Integer pageSize;

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

  public List<ActivityPackageDetail> getList() {
    return list;
  }

  public void setList(List<ActivityPackageDetail> list) {
    this.list = list;
  }

  public ActivityPackageDetail getActivityPackageDetail() {
    return activityPackageDetail;
  }

  public void setActivityPackageDetail(ActivityPackageDetail activityPackageDetail) {
    this.activityPackageDetail = activityPackageDetail;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public BigDecimal getTotalMoney() {
    return totalMoney;
  }

  public void setTotalMoney(BigDecimal totalMoney) {
    this.totalMoney = totalMoney;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public void setMoney(BigDecimal money) {
    this.money = money;
  }

  public BigDecimal getDiscountMoney() {
    return discountMoney;
  }

  public void setDiscountMoney(BigDecimal discountMoney) {
    this.discountMoney = discountMoney;
  }

  public Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }

  public Integer getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }
}
