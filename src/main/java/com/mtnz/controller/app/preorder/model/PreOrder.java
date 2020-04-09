package com.mtnz.controller.app.preorder.model;

import java.math.BigDecimal;
import java.util.Date;

public class PreOrder {

    private Long id;

    private String name;

    private Long customer_id;

    private String phone;

    private Long open_id;

    private String open_phone;

    private BigDecimal price;

    private BigDecimal total_price;

    private BigDecimal pre_price;

    private BigDecimal last_price;

    private String remark;

    private Date great_time;

    private Integer is_pass;

    private String address;

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

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getOpen_id() {
        return open_id;
    }

    public void setOpen_id(Long open_id) {
        this.open_id = open_id;
    }

    public String getOpen_phone() {
        return open_phone;
    }

    public void setOpen_phone(String open_phone) {
        this.open_phone = open_phone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public BigDecimal getPre_price() {
        return pre_price;
    }

    public void setPre_price(BigDecimal pre_price) {
        this.pre_price = pre_price;
    }

    public BigDecimal getLast_price() {
        return last_price;
    }

    public void setLast_price(BigDecimal last_price) {
        this.last_price = last_price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getGreat_time() {
        return great_time;
    }

    public void setGreat_time(Date great_time) {
        this.great_time = great_time;
    }

    public Integer getIs_pass() {
        return is_pass;
    }

    public void setIs_pass(Integer is_pass) {
        this.is_pass = is_pass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
