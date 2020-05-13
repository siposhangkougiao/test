package com.mtnz.controller.app.supplier.model;

import javax.persistence.*;

@Table(name = "supplier")
public class Supplier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "supplier_id")
  private Long supplierId;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "gname")
  private String gname;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "province")
  private String province;

  @Column(name = "city")
  private String city;

  @Column(name = "county")
  private String county;

  @Column(name = "street")
  private String street;

  @Column(name = "address")
  private String address;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "owe")
  private String owe;

  @Column(name = "date")
  private String date;

  @Column(name = "billing_date")
  private String billingDate;

  @Column(name = "status")
  private String status;

  @Column(name = "advance_charge")
  private String advanceCharge;

  @Column(name = "prepayment")
  private String prepayment;

  @Column(name = "identity")
  private String identity;

  @Column(name = "img")
  private String img;

  @Column(name = "management_img")
  private String managementImg;

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public String getGname() {
    return gname;
  }

  public void setGname(String gname) {
    this.gname = gname;
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getOwe() {
    return owe;
  }

  public void setOwe(String owe) {
    this.owe = owe;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getBillingDate() {
    return billingDate;
  }

  public void setBillingDate(String billingDate) {
    this.billingDate = billingDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAdvanceCharge() {
    return advanceCharge;
  }

  public void setAdvanceCharge(String advanceCharge) {
    this.advanceCharge = advanceCharge;
  }

  public String getPrepayment() {
    return prepayment;
  }

  public void setPrepayment(String prepayment) {
    this.prepayment = prepayment;
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getManagementImg() {
    return managementImg;
  }

  public void setManagementImg(String managementImg) {
    this.managementImg = managementImg;
  }
}
