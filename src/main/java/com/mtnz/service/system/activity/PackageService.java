package com.mtnz.service.system.activity;


import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.activity.model.ActivityPackage;

public interface PackageService {

    /**
     * 添加套餐
     * @param activityPackage
     */
    void insert(ActivityPackage activityPackage);

    /**
     * 修改套餐
     * @param activityPackage
     */
    void update(ActivityPackage activityPackage);

    /**
     * 查询列表
     * @param activityPackage
     * @return
     */
    PageInfo select(ActivityPackage activityPackage);
}
