package com.mtnz.service.system.customer;


import com.mtnz.controller.app.customer.model.Customer;
import com.mtnz.controller.app.supplierbalance.model.ReturnBean;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceDetail;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceOwe;

public interface CustomerNewService {

    /**
     * 添加客户（急速开单）
     * @param customer
     * @return
     */
    Long insert(Customer customer);

    /**
     * 根据编号查询客户信息
     * @param customer
     * @return
     */
    Customer selectByNumber(Customer customer);
}
