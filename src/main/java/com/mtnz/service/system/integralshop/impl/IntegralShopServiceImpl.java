package com.mtnz.service.system.integralshop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.integralshop.model.IntegralShopDetail;
import com.mtnz.controller.app.integralshop.model.IntegralShopUser;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.integralshop.IntegralShopService;
import com.mtnz.sql.system.integralshop.IntegralShopDetailMapper;
import com.mtnz.sql.system.integralshop.IntegralShopUserMapper;
import com.mtnz.util.MyTimesUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class IntegralShopServiceImpl implements IntegralShopService {

    @Resource
    IntegralShopUserMapper integralShopUserMapper;

    @Resource
    IntegralShopDetailMapper integralShopDetailMapper;

    /**
     * 积分商城首页
     * @param integralShopUser
     */
    @Override
    @Transactional
    public IntegralShopUser index(IntegralShopUser integralShopUser) {
        IntegralShopUser bean = integralShopUserMapper.selectOne(integralShopUser);
        if(bean==null){
            integralShopUserMapper.insertSelective(integralShopUser);
        }
        Example example = new Example(IntegralShopDetail.class);
        example.and().andBetween("creatTime", MyTimesUtil.getBeginDayOfWeek(),MyTimesUtil.getEndDayOfWeek());
        example.and().andEqualTo("userId",integralShopUser.getUserId());
        List<IntegralShopDetail> list = integralShopDetailMapper.selectByExample(example);
        bean.setList(list);
        return bean;
    }

    /**
     * 签到添加积分
     * @param bean
     */
    @Override
    @Transactional
    public void insertintegral(IntegralShopDetail bean) {
        Example example = new Example(IntegralShopDetail.class);
        example.and().andBetween("creatTime",MyTimesUtil.getStartTime(),MyTimesUtil.getEndTime());
        example.and().andEqualTo("userId",bean.getUserId());
        if(integralShopDetailMapper.selectCountByExample(example)>0){
            throw new ServiceException(-102,"今日已签到",null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        IntegralShopDetail integralShopDetail = new IntegralShopDetail();
        integralShopDetail.setUserId(bean.getUserId());
        integralShopDetail.setStoreId(bean.getStoreId());
        integralShopDetail.setIntegral(bean.getIntegral());
        integralShopDetail.setToday(sdf.format(new Date()));
        integralShopDetail.setType(bean.getType());
        integralShopDetailMapper.insertSelective(integralShopDetail);
        IntegralShopUser seBean = new IntegralShopUser();
        seBean.setUserId(bean.getUserId());
        IntegralShopUser integralShopUser = integralShopUserMapper.selectOne(seBean);
        if(integralShopUser==null){
            IntegralShopUser integralShopUser1 = new IntegralShopUser();
            integralShopUser1.setUserId(bean.getUserId());
            integralShopUser1.setStoreId(bean.getStoreId());
            integralShopUser1.setIntegral(bean.getIntegral());
            integralShopUserMapper.insertSelective(integralShopUser1);
        }else {
            integralShopUser.setIntegral(integralShopUser.getIntegral().add(bean.getIntegral()));
            integralShopUserMapper.updateByPrimaryKeySelective(integralShopUser);
        }
    }

    /**
     * 查询积分明细
     * @param integralShopDetail
     * @return
     */
    @Override
    public PageInfo selectDetail(IntegralShopDetail integralShopDetail) {
        PageHelper.startPage(integralShopDetail.getPageNumber(),integralShopDetail.getPageSize());
        Example example = new Example(IntegralShopDetail.class);
        example.and().andEqualTo("userId",integralShopDetail.getUserId());
        example.orderBy("id").desc();
        List<IntegralShopDetail> list = integralShopDetailMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
