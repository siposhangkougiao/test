package com.mtnz.controller.app.statistical.model;

import java.util.Date;
import java.util.List;

public class ReturnSelectSale {

    List<ReturnStore> returnStores;

    Date start;

    Date end;

    public List<ReturnStore> getReturnStores() {
        return returnStores;
    }

    public void setReturnStores(List<ReturnStore> returnStores) {
        this.returnStores = returnStores;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
