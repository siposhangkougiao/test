package com.mtnz.service.system.statistical;


import com.mtnz.controller.app.statistical.model.ReturnBean;
import com.mtnz.controller.app.statistical.model.ReturnSelectSale;
import com.mtnz.controller.app.statistical.model.ReturnStore;
import com.mtnz.controller.app.statistical.model.StBean;

import java.util.List;

public interface StatisticalService {

    /**
     * 查询日注册量、开单量、活跃量
     * @param stBean
     */
    ReturnBean select(StBean stBean);

    /**
     * 记录登陆次数
     * @param uid
     */
    void insertLogin(Long uid);

    /**
     * 查询所有店铺的销售额利润等信息
     * @param stBean
     * @return
     */
    ReturnSelectSale selectSale(StBean stBean);
}
