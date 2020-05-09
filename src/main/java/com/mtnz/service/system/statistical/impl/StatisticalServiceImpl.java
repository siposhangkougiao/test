package com.mtnz.service.system.statistical.impl;

import com.alibaba.fastjson.JSONObject;
import com.mtnz.controller.app.order.model.OrderInfo;
import com.mtnz.controller.app.order.model.OrderPro;
import com.mtnz.controller.app.statistical.model.*;
import com.mtnz.controller.app.store.model.Store;
import com.mtnz.controller.app.store.model.StoreUser;
import com.mtnz.service.system.statistical.StatisticalService;
import com.mtnz.service.system.store.StoreService;
import com.mtnz.sql.system.order_info.OrderInfoMapper;
import com.mtnz.sql.system.order_pro.OrderProMapper;
import com.mtnz.sql.system.statistical.StLoginMapper;
import com.mtnz.sql.system.store.StoreMapper;
import com.mtnz.sql.system.store.StoreUserMapper;
import com.mtnz.sql.system.sys_app_user.SysAppUserMapper;
import com.mtnz.util.MyTimesUtil;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatisticalServiceImpl implements StatisticalService {

    @Resource
    SysAppUserMapper sysAppUserMapper;

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    StLoginMapper stLoginMapper;

    @Resource
    StoreUserMapper storeUserMapper;

    @Resource
    OrderProMapper orderProMapper;

    @Resource
    StoreMapper storeMapper;


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
        Example examplelogin = new Example(SysAppUser.class);
        examplelogin.and().andBetween("loginDate",start,end);
        Integer loginCount = sysAppUserMapper.selectCountByExample(examplelogin);
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

    /**
     * 查询所有店铺的销售额利润等信息
     * @param stBean
     * @return
     */
    @Override
    public ReturnSelectSale selectSale(StBean stBean) {
        if(stBean.getType()==1){//天
            stBean.setStart(MyTimesUtil.getStartTime());
            stBean.setEnd(MyTimesUtil.getEndTime());
        }
        if(stBean.getType()==2){//周
            stBean.setStart(MyTimesUtil.getBeginDayOfWeek());
            stBean.setEnd(MyTimesUtil.getEndDayOfWeek());
        }
        if(stBean.getType()==3){//月
            stBean.setStart(MyTimesUtil.getBeginDayOfMonth());
            stBean.setEnd(MyTimesUtil.getEndDayOfMonth());
        }
        if(stBean.getType()==4){//年
            stBean.setStart(MyTimesUtil.getYearStartTime(new Date().getTime(),"GMT+8:00"));
            stBean.setEnd(MyTimesUtil.getYearEndTime(new Date().getTime(),"GMT+8:00"));
        }
        System.out.println(JSONObject.toJSONString(stBean));
        Example exampleuser =new Example(StoreUser.class);
        List<ReturnStore> returnStores = new ArrayList<>();
        exampleuser.and().andEqualTo("userId",stBean.getUserId());
        exampleuser.and().andEqualTo("status",0);
        List<StoreUser> stores = storeUserMapper.selectByExample(exampleuser);
        for (int i = 0; i < stores.size(); i++) {
            Long storeId= stores.get(i).getStoreId();
            Example example = new Example(OrderInfo.class);
            example.and().andEqualTo("storeId",storeId);
            example.and().andEqualTo("revokes",0);
            if(stBean.getStart()!=null&&stBean.getEnd()!=null){
                example.and().andBetween("date",stBean.getStart(),stBean.getEnd());
            }
            List<OrderInfo> list = orderInfoMapper.selectByExample(example);
            BigDecimal sale = new BigDecimal(0);//销售额
            BigDecimal chengben = new BigDecimal(0);//成本价
            for (int j = 0; j < list.size(); j++) {
                sale = sale.add(new BigDecimal(list.get(j).getMoney()));
                Example examplePro = new Example(OrderPro.class);
                examplePro.and().andEqualTo("orderInfoId",list.get(j).getOrderInfoId());
                List<OrderPro> pros = orderProMapper.selectByExample(examplePro);
                for (int k = 0; k <pros.size() ; k++) {
                    if(pros.get(k).getAllNumber().compareTo(new BigDecimal(0))==1){
                        BigDecimal aa = pros.get(k).getNowNumber().divide(new BigDecimal(pros.get(k).getNorms1()),4,BigDecimal.ROUND_HALF_UP);
                        chengben = chengben.add(new BigDecimal(pros.get(k).getPurchasePrice()).multiply(aa));
                    }else {
                        chengben =chengben.add(new BigDecimal(pros.get(k).getPurchasePrice()).multiply(new BigDecimal(pros.get(k).getNum())));
                    }
                }
            }
            BigDecimal profit = sale.subtract(chengben);
            ReturnStore returnStore = new ReturnStore();
            returnStore.setSale(sale);
            returnStore.setProfit(profit);

            Store store = new Store();
            store.setStoreId(storeId);
            Store store1 = storeMapper.selectOne(store);
            if(store1!=null){
                returnStore.setStoreName(store1.getName());
            }else {
                returnStore.setStoreName("");
            }
            returnStores.add(returnStore);
        }
        ReturnSelectSale returnSelectSale = new ReturnSelectSale();
        returnSelectSale.setReturnStores(returnStores);
        returnSelectSale.setStart(stBean.getStart());
        returnSelectSale.setEnd(stBean.getEnd());
        return returnSelectSale;
    }
}
