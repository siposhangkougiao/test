package com.mtnz.controller.app.supplierbalance.model;

import com.github.pagehelper.PageInfo;

public class ReturnBean {

    private PageInfo pageInfo;

    private SupplierBalanceOwe supplierBalanceOwe;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public SupplierBalanceOwe getSupplierBalanceOwe() {
        return supplierBalanceOwe;
    }

    public void setSupplierBalanceOwe(SupplierBalanceOwe supplierBalanceOwe) {
        this.supplierBalanceOwe = supplierBalanceOwe;
    }
}
