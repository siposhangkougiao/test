package com.mtnz.service.system.product;


import com.mtnz.controller.app.product.model.KuCun;

public interface KuCunNewService {

    /**
     * 查询进货库存信息
     * @param kuCun
     * @return
     */
    KuCun selectKuCun(KuCun kuCun);

    /**
     * 修改现有数量
     * @param upkucun
     */
    void updateNums(KuCun upkucun);
}
