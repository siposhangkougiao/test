package com.mtnz.service.system.supplierbalance;


import com.mtnz.controller.app.supplierbalance.model.ReturnBean;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceDetail;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceOwe;

public interface SupplierBalanceService {

    /**
     * 查询预付款明细列表
     * @param supplierBalanceOwe
     * @return
     */
    ReturnBean select(SupplierBalanceOwe supplierBalanceOwe) throws Exception;

    /**
     * 添加明细
     * @param supplierBalanceDetail
     * @return
     */
    int insert(SupplierBalanceDetail supplierBalanceDetail) throws Exception;

    /**
     * 查询预付款明细
     * @param id
     * @return
     */
    SupplierBalanceOwe selectOne(Long id) throws Exception;

    /**
     * 撤单
     * @param id
     * @return
     */
    int reback(Long id,Long uid);

    /**
     * 初始化金额
     * @param supplierBalanceOwe
     * @return
     */
    int updatebegin(SupplierBalanceOwe supplierBalanceOwe);

    /**
     * 查询用户在供货商处的预存款
     * @param supplierBalanceOwe
     * @return
     */
    SupplierBalanceOwe selectbalance(SupplierBalanceOwe supplierBalanceOwe);
}
