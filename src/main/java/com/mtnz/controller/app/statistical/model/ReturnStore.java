package com.mtnz.controller.app.statistical.model;

import java.math.BigDecimal;

public class ReturnStore {

    private String storeName;

    private BigDecimal Sale;

    private BigDecimal profit;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public BigDecimal getSale() {
        return Sale;
    }

    public void setSale(BigDecimal sale) {
        Sale = sale;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
