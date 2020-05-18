package com.mtnz.service.system.preorder.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.app.preorder.model.PreOrderDetail;
import com.mtnz.controller.app.preorder.model.SelectListBean;
import com.mtnz.service.system.preorder.PreOrderService;
import com.mtnz.sql.system.preorder.PreOrderDetailMapper;
import com.mtnz.sql.system.preorder.PreOrderMapper;
import com.mtnz.util.MyTimesUtil;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class PreOrderServiceImpl implements PreOrderService {
    @Resource
    private PreOrderMapper preOrderMapper;

    @Resource
    private PreOrderDetailMapper preOrderDetailMapper;

    /**
     * 添加预支付订单
     * @param preOrder
     */
    @Override
    public void insert(PreOrder preOrder) {
        preOrderMapper.insertSelective(preOrder);
        for (int i = 0; i < preOrder.getList().size(); i++) {
            PreOrderDetail preOrderDetail = preOrder.getList().get(i);
            preOrderDetail.setOrder_id(preOrder.getId());
            preOrderDetail.setStore_id(preOrder.getStore_id());
            preOrderDetailMapper.insertSelective(preOrderDetail);
        }
    }

    /**
     * 预售单列表
     * @param preOrder
     * @return
     */
    @Override
    public SelectListBean selectlist(PreOrder preOrder){
        PageHelper.startPage(preOrder.getPageNumber(),preOrder.getPageSize());
        if(preOrder.getStar_time()==null||preOrder.getEnd_time()==null){
            preOrder.setStar_time((String) MyTimesUtil.getDayTSan().get("startDate"));
            preOrder.setEnd_time((String)MyTimesUtil.getDayTSan().get("endDate"));
        }
        List<PreOrder> list  = preOrderMapper.selectlist(preOrder);
        PageInfo pageInfo = new PageInfo(list);
        List<PreOrder> relist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PreOrder poder = preOrderMapper.selectByPrimaryKey(list.get(i).getId());
            PreOrderDetail pod = new PreOrderDetail();
            pod.setOrder_id(list.get(i).getId());
            List<PreOrderDetail> details = preOrderDetailMapper.select(pod);
            poder.setList(details);
            relist.add(poder);
        }
        pageInfo.setList(relist);
        BigDecimal total_price = new BigDecimal(0);
        List<PreOrder> listprice  = preOrderMapper.selectlist(preOrder);
        for (int i = 0; i < listprice.size(); i++) {
            PreOrder poder = preOrderMapper.selectByPrimaryKey(listprice.get(i).getId());
            total_price = total_price.add(poder.getPrice());
        }
        SelectListBean selectListBean = new SelectListBean();
        selectListBean.setTotal_price(total_price);
        selectListBean.setPageInfo(pageInfo);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(JSONObject.toJSONString(selectListBean));
        return selectListBean;
    }

    /**
     * 查询单个预售单
     * @param id
     * @return
     */
    @Override
    public PreOrder selectById(Long id) {
        PreOrder poder = preOrderMapper.selectByPrimaryKey(id);
        PreOrderDetail pod = new PreOrderDetail();
        pod.setOrder_id(id);
        List<PreOrderDetail> details = preOrderDetailMapper.select(pod);
        poder.setList(details);
        return poder;
    }

    /**
     * 更新预售单
     * @param preOrder
     */
    @Override
    public void update(PreOrder preOrder) {
        preOrderMapper.updateByPrimaryKeySelective(preOrder);
        if(preOrder.getList()!=null&&preOrder.getList().size()>0){
            PreOrderDetail delete = new PreOrderDetail();
            delete.setOrder_id(preOrder.getId());
            preOrderDetailMapper.delete(delete);
            for (int i = 0; i < preOrder.getList().size(); i++) {
                PreOrderDetail preOrderDetail = preOrder.getList().get(i);
                preOrderDetail.setOrder_id(preOrder.getId());
                preOrderDetailMapper.insertSelective(preOrderDetail);
            }
        }
    }

    @Override
    public Integer selectisreturn(PageData pd_o) {
        Integer isreturn = 0;
        if(pd_o.get("order_info_id")!=null){
            PreOrder preOrder = new PreOrder();
            preOrder.setOrderId(Long.valueOf(pd_o.get("order_info_id").toString()));
            isreturn = preOrderMapper.selectCount(preOrder);
        }

        return isreturn;
    }

    @Override
    public PreOrder selectPreOrderByOrderInfo(PageData pd_o) {
        if(pd_o.get("order_info_id")!=null){
            PreOrder preOrder = new PreOrder();
            preOrder.setOrderId(Long.valueOf(pd_o.get("order_info_id").toString()));
            PreOrder retuen = preOrderMapper.selectOne(preOrder);
            return retuen;
        }
        return null;
    }
}
