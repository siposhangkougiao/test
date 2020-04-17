package com.mtnz.service.system.preorder;


import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.app.preorder.model.SelectListBean;
import com.mtnz.util.PageData;

import java.text.ParseException;

public interface PreOrderService {

    /**
     * 添加预支付订单
     * @param preOrder
     */
    void insert(PreOrder preOrder);

    /**
     * 预售单列表
     * @param preOrder
     * @return
     */
    SelectListBean selectlist(PreOrder preOrder) throws ParseException;

    /**
     * 查询单个预售单
     * @param id
     * @return
     */
    PreOrder selectById(Long id);

    /**
     * 修改预售单
     * @param preOrder
     */
    void update(PreOrder preOrder);

    Integer selectisreturn(PageData pd_o);

    PreOrder selectPreOrderByOrderInfo(PageData pd_o);
}
