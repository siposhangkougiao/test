package com.mtnz.controller.app.preorder.model;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "pre_order")
public class PreOrder {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "customer_id")
    private Long customer_id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "open_id")
    private Long open_id;

    @Column(name = "open_phone")
    private String open_phone;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "total_price")
    private BigDecimal total_price;

    @Column(name = "pre_price")
    private BigDecimal pre_price;

    @Column(name = "last_price")
    private BigDecimal last_price;

    @Column(name = "remark")
    private String remark;

    @Column(name = "great_time")
    private Date great_time;

    @Column(name = "is_pass")
    private Integer is_pass;

    @Column(name = "address")
    private String address;

    @Column(name = "store_id")
    private Long store_id;

    @Column(name = "is_return")
    private Integer is_return;

    @Column(name = "order_id")
    private Long orderId;

    @Transient
    private List<PreOrderDetail> list;

    @Transient
    private Integer pageNumber;

    @Transient
    private Integer pageSize;
    /**
     * 搜索条件商品id
     */
    @Transient
    private String productName;

    @Transient
    private String star_time;

    @Transient
    private String end_time;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public Integer getIs_return() {
        return is_return;
    }

    public void setIs_return(Integer is_return) {
        this.is_return = is_return;
    }

    public List<PreOrderDetail> getList() {
        return list;
    }

    public void setList(List<PreOrderDetail> list) {
        this.list = list;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStar_time() {
        return star_time;
    }

    public void setStar_time(String star_time) {
        this.star_time = star_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
