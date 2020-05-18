package com.mtnz.controller.app.user.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "validation_yzm")
public class ValidationYzm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "start_time")
  private Date startTime;

  @Column(name = "end_time")
  private Date endTime;

  @Column(name = "phone")
  private String phone;

  @Column(name = "code")
  private String code;

  @Column(name = "is_use")
  private Integer isUse;

  @Column(name = "use_type")
  private Integer useType;

  @Transient
  private Integer type;

  public Integer getUseType() {
    return useType;
  }

  public void setUseType(Integer useType) {
    this.useType = useType;
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

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getIsUse() {
    return isUse;
  }

  public void setIsUse(Integer isUse) {
    this.isUse = isUse;
  }
}
