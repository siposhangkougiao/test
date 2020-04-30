package com.mtnz.service.system.mysql.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.mtnz.controller.app.mysql.model.GetBean;
import com.mtnz.controller.app.mysql.model.KuCun;
import com.mtnz.controller.app.mysql.model.Product;
import com.mtnz.service.system.mysql.KuCunService;
import com.mtnz.service.system.sys_app_user.SysAppUserService;
import com.mtnz.sql.system.mysql.KuCunMapper;
import com.mtnz.sql.system.mysql.ProductMapper;
import com.mtnz.util.PageData;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mtnz.util.QiniuUtils.getAllFileName;

@Service
public class KuCunServiceImpl implements KuCunService {

    @Resource
    ProductMapper productMapper;

    @Resource
    KuCunMapper kuCunMapper;

    @Resource(name = "sysAppUserService")
    SysAppUserService sysAppUserService;
    @Override
    public void test() {
        PageHelper.startPage(1,10000);
        List<Product> list = productMapper.selectAll();
        List<Long> relist = new ArrayList<>();
        List<KuCun> updatelist = new ArrayList<>();
        List<KuCun> updateling = new ArrayList<>();
        List<GetBean> getBeans = kuCunMapper.selectgetbean(list);
        Map<Long,Integer> map = new HashedMap();
        for (int i = 0; i < getBeans.size(); i++) {
            map.put(getBeans.get(i).getProductId(),getBeans.get(i).getTotal());
        }
        for (int i = 0; i < list.size(); i++) {
            /*Integer allnumber = kuCunMapper.selectSum(list.get(i).getProductId());*/
            if(map.containsKey(list.get(i).getProductId())&&list.get(i).getKucun()!=map.get(list.get(i).getProductId())){
                KuCun kuCun = new KuCun();
                kuCun.setProductId(list.get(i).getProductId());
                kuCun.setNums(0);
                updateling.add(kuCun);
                /*kuCunMapper.updatekucun(kuCun);*/
                KuCun kubean = kuCunMapper.selectdesc(list.get(i).getProductId());

                if(kubean!=null){
                    System.out.println("查询出来的kucun_id："+JSONObject.toJSONString(kubean));
                    KuCun kuCun1 = new KuCun();
                    kuCun1.setKuncunId(kubean.getKuncunId());
                    kuCun1.setNums(list.get(i).getKucun());
                    updatelist.add(kuCun1);
                    /*int count = kuCunMapper.updateByPrimaryKeySelective(kuCun1);
                    if(count>0){
                        System.out.println(">>>>>>>>>>>>"+"更新了一条数据");
                    }*/
                    relist.add(list.get(i).getProductId());
                }
            }
        }
        kuCunMapper.updateling(updateling);
        kuCunMapper.updatelist(updatelist);
        System.out.println(JSONObject.toJSONString(relist));
    }

    /**
     * 发送维护通知
     */
    @Override
    public void sendMeg() throws Exception {
        List<PageData> list =sysAppUserService.findallUser();
        String aa = "";
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("username")!=null){
                String username = list.get(i).get("username").toString();
                if(username.length()==11){
                    aa = aa + username + ";";
                }
            }
        }
        System.out.println(">>>>>>"+aa);
    }

    /**
     * 刷新数据库图片地址(更新商品图片)
     */
    @Override
    public void image() {
        ArrayList<String> listFilePath = new ArrayList<String>();
        ArrayList<String> listFileName = new ArrayList<String>();
        getAllFileName("C:\\Users\\Dell\\Desktop\\uploadImgs\\",listFilePath,listFileName);
        List<Product> insertlist = new ArrayList<>();
        for (int i = 0; i < listFileName.size(); i++) {
            String name = listFileName.get(i);
            if(name.contains(".jpg")){
                Product bean = new Product();
                bean.setName(name);
                Product product = productMapper.selectlikeimg(bean);
                if(product!=null){
                    if(StringUtils.isBlank(product.getProductImg())){
                        continue;
                    }else {
                        if(product.getProductImg().contains("img.nongshoping.com")){
                            continue;
                        }
                    }
                    String aa = "http://img.nongshoping.com/xkd" + name;
                    System.out.println(aa);
                    Product product1 = new Product();
                    product1.setProductId(product.getProductId());
                    product1.setProductImg(aa);
                    insertlist.add(product1);
                }
            }
        }
        System.out.println(">>>>>>>"+insertlist.size());
        if(insertlist.size()>0){
            productMapper.updatelist(insertlist);
        }
    }
}
