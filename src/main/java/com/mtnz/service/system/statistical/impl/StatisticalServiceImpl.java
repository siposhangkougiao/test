package com.mtnz.service.system.statistical.impl;

import com.mtnz.controller.app.order.model.OrderInfo;
import com.mtnz.controller.app.statistical.model.ReturnBean;
import com.mtnz.controller.app.statistical.model.StBean;
import com.mtnz.controller.app.statistical.model.StLogin;
import com.mtnz.controller.app.statistical.model.SysAppUser;
import com.mtnz.service.system.statistical.StatisticalService;
import com.mtnz.sql.system.order_info.OrderInfoMapper;
import com.mtnz.sql.system.statistical.StLoginMapper;
import com.mtnz.sql.system.sys_app_user.SysAppUserMapper;
import com.mtnz.util.MyTimesUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StatisticalServiceImpl implements StatisticalService {

    @Resource
    SysAppUserMapper sysAppUserMapper;

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    StLoginMapper stLoginMapper;


    /**
     * 查询日注册量、开单量、活跃量
     * @param stBean
     */
    @Override
    public ReturnBean select(StBean stBean) {
        Date start = MyTimesUtil.getDayStartTime(stBean.getStart());
        Date end = MyTimesUtil.getDayEndTime(stBean.getEnd());

        //查询日注册量
        Example exampleregist = new Example(SysAppUser.class);
        exampleregist.and().andBetween("date",start,end);
        Integer registCount = sysAppUserMapper.selectCountByExample(exampleregist);

        //查询开单量
        Example exampleOrder = new Example(OrderInfo.class);
        exampleOrder.and().andBetween("date",start,end);
        exampleOrder.and().andEqualTo("revokes",0);
        Integer orderCount = orderInfoMapper.selectCountByExample(exampleOrder);

        //查询活跃度
        Example examplelogin = new Example(StLogin.class);
        examplelogin.and().andBetween("creatTime",start,end);
        Integer loginCount = stLoginMapper.selectCountByExample(examplelogin);
        ReturnBean returnBean = new ReturnBean();
        returnBean.setOrderCount(orderCount);
        returnBean.setLoginCount(loginCount);
        returnBean.setRegistCount(registCount);
        return returnBean;
    }

    /**
     * 记录登陆次数
     * @param uid
     */
    @Override
    public void insertLogin(Long uid) {
        Date time = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(time);
        StLogin stLogin = new StLogin();
        stLogin.setUserId(uid);
        stLogin.setCreatTime(time);
        stLogin.setDate(date);
        stLoginMapper.insertSelective(stLogin);
    }
}
