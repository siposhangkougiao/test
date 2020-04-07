package com.mtnz.controller.app.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.order_info.OrderInfoService;
import com.mtnz.service.system.order_kuncun.OrderKuncunService;
import com.mtnz.service.system.order_pro.OrderProService;
import com.mtnz.service.system.product.KunCunService;
import com.mtnz.service.system.product.ProductImgService;
import com.mtnz.service.system.product.ProductService;
import com.mtnz.service.system.product.WeProductService;
import com.mtnz.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/*
    Created by xxj on 2018\3\22 0022.  
 */
@Controller
@RequestMapping(value = "app/myproduct",produces = "text/html;charset=UTF-8")
public class AppProductMyController extends BaseController{

    @Resource(name = "productService")
    private ProductService productService;
    @Resource(name = "orderProService")
    private OrderProService orderProService;
    @Resource(name = "orderInfoService")
    private OrderInfoService orderInfoService;
    @Resource(name = "orderKuncunService")
    private OrderKuncunService orderKuncunService;

    /**
     * 商品销售额对比
     * @param product_id 商品id
     * @param status 1至   2或
     * @param start  开始时间
     * @param end 结束时间
     * @return
     */
    @RequestMapping(value = "findcontrast",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProductFeiXis(Long product_id,Integer status, String start,String  end ){
        logBefore(logger,"查询详情");
        PageData pd=this.getPageData();
        List<Map> relist = new ArrayList<>();
        try{
            if(status==1){//时间段
                List<String> list = MyTimesUtil.getRangeSet(start,end);
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashedMap();
                    String [] a = list.get(i).split("-");
                    Date beginTime = MyTimesUtil.getBeginTime(Integer.valueOf(a[0]),Integer.valueOf(a[1]));//开始时间
                    Date endTime = MyTimesUtil.getEndTime(Integer.valueOf(a[0]),Integer.valueOf(a[1]));//结束时间
                    PageData tx = new PageData();
                    tx.put("product_id",product_id);
                    tx.put("beginTime",beginTime);
                    tx.put("endTime",endTime);
                    BigDecimal ap = orderKuncunService.findOrderKucunTsByIdAndTime(tx);//查询出来整袋的钱
                    map.put("date",list.get(i));

                    if(ap.compareTo(new BigDecimal(0))<1){//说明这是拆袋销售
                        PageData pageData = productService.findById(tx);
                        BigDecimal apd = orderKuncunService.findOrderKucunTsLiByIdAndTime(tx);
                        if(apd.compareTo(new BigDecimal(0))>0){
                            BigDecimal dd = apd.divide(new BigDecimal(pageData.get("norms1").toString()),4,BigDecimal.ROUND_HALF_UP);
                            ap = ap.add(dd);
                        }
                    }
                    map.put("money",ap);
                    relist.add(map);
                }
            }else {//时间点
                Map map = new HashedMap();
                String [] a1 = start.split("-");
                Date beginTime1 = MyTimesUtil.getBeginTime(Integer.valueOf(a1[0]),Integer.valueOf(a1[1]));//开始时间
                Date endTime1 = MyTimesUtil.getEndTime(Integer.valueOf(a1[0]),Integer.valueOf(a1[1]));//结束时间
                PageData tx = new PageData();
                tx.put("product_id",product_id);
                tx.put("beginTime",beginTime1);
                tx.put("endTime",endTime1);
                BigDecimal ap1 = orderKuncunService.findOrderKucunTsByIdAndTime(tx);
                map.put("date",start);

                if(ap1.compareTo(new BigDecimal(0))<1){//说明这是拆袋销售
                    PageData pageData = productService.findById(tx);
                    BigDecimal apd = orderKuncunService.findOrderKucunTsLiByIdAndTime(tx);
                    if(apd.compareTo(new BigDecimal(0))>0){
                        BigDecimal dd = apd.divide(new BigDecimal(pageData.get("norms1").toString()),4,BigDecimal.ROUND_HALF_UP);
                        ap1 = ap1.add(dd);
                    }
                }
                map.put("money",ap1);
                relist.add(map);


                Map map2 = new HashedMap();
                String [] a2 = end.split("-");
                Date beginTime2 = MyTimesUtil.getBeginTime(Integer.valueOf(a2[0]),Integer.valueOf(a2[1]));//开始时间
                Date endTime2 = MyTimesUtil.getEndTime(Integer.valueOf(a2[0]),Integer.valueOf(a2[1]));//结束时间
                PageData tx2 = new PageData();
                tx2.put("product_id",product_id);
                tx2.put("beginTime",beginTime2);
                tx2.put("endTime",endTime2);
                BigDecimal ap2 = orderKuncunService.findOrderKucunTsByIdAndTime(tx2);
                map2.put("date",end);
                if(ap2.compareTo(new BigDecimal(0))<1){//说明这是拆袋销售
                    PageData pageData = productService.findById(tx2);
                    BigDecimal apd = orderKuncunService.findOrderKucunTsLiByIdAndTime(tx2);
                    if(apd.compareTo(new BigDecimal(0))>0){
                        BigDecimal dd = apd.divide(new BigDecimal(pageData.get("norms1").toString()),4,BigDecimal.ROUND_HALF_UP);
                        ap2 = ap2.add(dd);
                    }
                }
                map2.put("money",ap2);


                relist.add(map2);
            }
            pd.clear();
            pd.put("data",relist);
            pd.put("code","1");
            pd.put("message","正确返回数据!");
        }catch (Exception e){
            pd.clear();
            pd.put("code","2");
            pd.put("message","程序出错,请联系管理员!");
            e.printStackTrace();
        }
        ObjectMapper mapper=new ObjectMapper();
        String str="";
        try {
            str= mapper.writeValueAsString(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }



}
