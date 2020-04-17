package com.mtnz.controller.app.preorder.model;

import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;

public class SelectListBean {

    private BigDecimal total_price;

    private PageInfo pageInfo;

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
