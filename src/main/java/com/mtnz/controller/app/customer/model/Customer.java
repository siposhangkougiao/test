package com.mtnz.controller.app.customer.model;

import javax.persistence.*;

@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "number")
  private Integer number;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "address")
  private String address;

  @Column(name = "crop")
  private String crop;

  @Column(name = "area")
  private String area;

  @Column(name = "input_date")
  private String inputDate;

  @Column(name = "billing_date")
  private String billingDate;

  @Column(name = "owe")
  private String owe;

  @Column(name = "status")
  private String status;

  @Column(name = "uid")
  private String uid;

  @Column(name = "province")
  private String province;

  @Column(name = "city")
  private String city;

  @Column(name = "county")
  private String county;

  @Column(name = "street")
  private String street;

  @Column(name = "prepayment")
  private String prepayment;

  @Column(name = "integral")
  private String integral;

  @Column(name = "img")
  private String img;

  @Column(name = "identity")
  private String identity;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "role")
  private Integer role;

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCrop() {
    return crop;
  }

  public void setCrop(String crop) {
    this.crop = crop;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getInputDate() {
    return inputDate;
  }

  public void setInputDate(String inputDate) {
    this.inputDate = inputDate;
  }

  public String getBillingDate() {
    return billingDate;
  }

  public void setBillingDate(String billingDate) {
    this.billingDate = billingDate;
  }

  public String getOwe() {
    return owe;
  }

  public void setOwe(String owe) {
    this.owe = owe;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPrepayment() {
    return prepayment;
  }

  public void setPrepayment(String prepayment) {
    this.prepayment = prepayment;
  }

  public String getIntegral() {
    return integral;
  }

  public void setIntegral(String integral) {
    this.integral = integral;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = role;
  }
}
