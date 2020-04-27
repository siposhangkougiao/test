package com.mtnz.controller.app.supplierbalance.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "supplier_balance_detail")
public class SupplierBalanceDetail {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 交易金额
     */
    @Column(name = "money")
    private BigDecimal money;

    /**
     * 优惠金额
     */
    @Column(name = "discount_money")
    private BigDecimal discountMoney;

    /**
     * 实收金额
     */
    @Column(name = "total_money")
    private BigDecimal totalMoney;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "creat_time")
    private Date creatTime;

    /**
     * 店铺id
     */
    @Column(name = "store_id")
    private Long storeId;

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
     * '1 充值  2还钱',
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 0 未撤单  1撤单
     */
    @Column(name = "is_back")
    private Integer isBack;

    /**
     * 撤单时间
     */
    @Column(name = "back_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date backTime;

    /**
     * 撤单人id
     */
    @Column(name = "back_id")
    private Long backId;

    /**
     * 撤单人名称
     */
    @Transient
    private String backName;

    @Transient
    private String openName;

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Long getBackId() {
        return backId;
    }

    public void setBackId(Long backId) {
        this.backId = backId;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
}
