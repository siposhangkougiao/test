package com.mtnz.controller.app.supplierbalance.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "supplier_balance_owe")
public class SupplierBalanceOwe {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 供货商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 店铺id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 供货商名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 欠款
     */
    @Column(name = "owe_price")
    private BigDecimal owePrice;

    /**
     * 欠款
     */
    @Column(name = "pre_price")
    private BigDecimal prePrice;

    @Transient
    private String remark;

    @Transient
    private Integer pageNumber =1;

    @Transient
    private Integer pageSize =10;

    @Transient
    private SupplierBalanceDetail supplierBalanceDetail;

    @Transient
    private Date CreatTime;

    /**
     * 开单人名称
     */
    @Transient
    private String open_name;

    @Transient
    private Integer type;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatTime() {
        return CreatTime;
    }

    public void setCreatTime(Date creatTime) {
        CreatTime = creatTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOpen_name() {
        return open_name;
    }

    public void setOpen_name(String open_name) {
        this.open_name = open_name;
    }

    public SupplierBalanceDetail getSupplierBalanceDetail() {
        return supplierBalanceDetail;
    }

    public void setSupplierBalanceDetail(SupplierBalanceDetail supplierBalanceDetail) {
        this.supplierBalanceDetail = supplierBalanceDetail;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOwePrice() {
        return owePrice;
    }

    public void setOwePrice(BigDecimal owePrice) {
        this.owePrice = owePrice;
    }

    public BigDecimal getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(BigDecimal prePrice) {
        this.prePrice = prePrice;
    }
}
