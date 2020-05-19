package com.mtnz.service.system.integralshop;


import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.integralshop.model.IntegralShopDetail;
import com.mtnz.controller.app.integralshop.model.IntegralShopUser;

public interface IntegralShopService {

    /**
     * 积分商城首页
     * @param integralShopUser
     */
    IntegralShopUser index(IntegralShopUser integralShopUser);

    /**
     * 签到添加积分
     * @param integralShopDetail
     */
    void insertintegral(IntegralShopDetail integralShopDetail);

    /**
     * 查询积分明细
     * @param integralShopDetail
     * @return
     */
    PageInfo selectDetail(IntegralShopDetail integralShopDetail);
}
