package com.mtnz.service.system.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.activity.model.ActivityPackage;
import com.mtnz.controller.app.activity.model.ActivityPackageDetail;
import com.mtnz.service.system.activity.PackageService;
import com.mtnz.sql.system.activity.ActivityPackageDetailMapper;
import com.mtnz.sql.system.activity.ActivityPackageMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    @Resource
    private ActivityPackageMapper activityPackageMapper;

    @Resource
    private ActivityPackageDetailMapper activityPackageDetailMapper;
    /**
     * 添加套餐
     * @param activityPackage
     */
    @Override
    public void insert(ActivityPackage activityPackage) {
        activityPackageMapper.insertSelective(activityPackage);
        Long id = activityPackage.getId();
        for (int i = 0; i <activityPackage.getList().size() ; i++) {
            ActivityPackageDetail activityPackageDetail = activityPackage.getList().get(i);
            activityPackageDetail.setPackageId(id);
            activityPackageDetailMapper.insertSelective(activityPackageDetail);
        }
    }

    /**
     * 修改套餐
     * @param activityPackage
     */
    @Override
    public void update(ActivityPackage activityPackage) {
        if(activityPackage.getId()!=null){
            activityPackageMapper.updateByPrimaryKeySelective(activityPackage);
        }
        if(activityPackage.getList().size()>0){
            for (int i = 0; i <activityPackage.getList().size() ; i++) {
                ActivityPackageDetail activityPackageDetail = activityPackage.getList().get(i);
                if(activityPackageDetail.getId()!=null){
                    activityPackageDetailMapper.updateByPrimaryKeySelective(activityPackageDetail);
                }
            }
        }
    }

    /**
     * 查询列表
     * @param activityPackage
     * @return
     */
    @Override
    public PageInfo select(ActivityPackage activityPackage) {
        Example example = new Example(ActivityPackage.class);
        example.and().andEqualTo("storeId",activityPackage.getStoreId());
        example.and().andEqualTo("isDelete",0);
        PageHelper.startPage(activityPackage.getPageNumber(),activityPackage.getPageSize());
        List<ActivityPackage> list = activityPackageMapper.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            Long id = list.get(i).getId();
            ActivityPackageDetail acd = new ActivityPackageDetail();
            acd.setPackageId(id);
            List<ActivityPackageDetail> details = activityPackageDetailMapper.select(acd);
            list.get(i).setList(details);
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
