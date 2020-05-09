package com.mtnz.controller.app.store.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "store")
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "name")
  private String name;

  @Column(name = "number")
  private Integer number;

  @Column(name = "address")
  private String address;

  @Column(name = "qr_code")
  private String qrCode;

  @Column(name = "province")
  private String province;

  @Column(name = "city")
  private String city;

  @Column(name = "county")
  private String county;

  @Column(name = "street")
  private String street;

  @Column(name = "phone")
  private String phone;

  @Column(name = "business_img")
  private String businessImg;

  @Column(name = "phoneTow")
  private String phoneTow;

  @Column(name = "door_first")
  private String doorFirst;

  @Column(name = "license_img")
  private String licenseImg;

  @Column(name = "is_pass")
  private Integer isPass;

  @Column(name = "pass_time")
  private Date passTime;

  @Column(name = "over_time")
  private Date overTime;

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

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getQrCode() {
    return qrCode;
  }

  public void setQrCode(String qrCode) {
    this.qrCode = qrCode;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getBusinessImg() {
    return businessImg;
  }

  public void setBusinessImg(String businessImg) {
    this.businessImg = businessImg;
  }

  public String getPhoneTow() {
    return phoneTow;
  }

  public void setPhoneTow(String phoneTow) {
    this.phoneTow = phoneTow;
  }

  public String getDoorFirst() {
    return doorFirst;
  }

  public void setDoorFirst(String doorFirst) {
    this.doorFirst = doorFirst;
  }

  public String getLicenseImg() {
    return licenseImg;
  }

  public void setLicenseImg(String licenseImg) {
    this.licenseImg = licenseImg;
  }

  public Integer getIsPass() {
    return isPass;
  }

  public void setIsPass(Integer isPass) {
    this.isPass = isPass;
  }

  public Date getPassTime() {
    return passTime;
  }

  public void setPassTime(Date passTime) {
    this.passTime = passTime;
  }

  public Date getOverTime() {
    return overTime;
  }

  public void setOverTime(Date overTime) {
    this.overTime = overTime;
  }
}
