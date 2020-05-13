package com.mtnz.service.system.supplier;


import com.mtnz.controller.app.supplier.model.Supplier;
import com.mtnz.controller.app.supplier.model.SupplierOrderPro;

public interface SupplieresService {

    /**
     * 修改详情剩余数量
     * @param supplierOrderPro
     */
    void update(SupplierOrderPro supplierOrderPro);

    /**
     * 查询供货商信息
     * @param supplierbean
     * @return
     */
    Supplier selectSupplier(Supplier supplierbean);
}
