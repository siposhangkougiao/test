package com.mtnz.controller.app.recharge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.recharge.RechargeService;
import com.mtnz.service.system.store.StoreService;
import com.mtnz.util.DateUtil;
import com.mtnz.util.MD5;
import com.mtnz.util.PageData;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\4\19 0019.  
 */
@Controller
@RequestMapping(value = "app/recharge",produces = "text/html;charset=UTF-8")
public class RechargeComtroller extends BaseController{
    @Resource(name = "rechargeService")
    private RechargeService rechargeService;
    @Resource(name = "storeService")
    private StoreService storeService;

    /**
     *
     * @param store_id 店铺ID
     * @param pageNum 页码
     * @return
     */
    @RequestMapping(value = "findRecharge",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findRecharge(String store_id,String pageNum){
        logBefore(logger,"查询充值记录");
        PageData pd=this.getPageData();
        Page page=new Page();
        String message="正确返回数据!";
        if(store_id==null||store_id.length()==0){
            pd.clear();
            pd.put("code","2");
            pd.put("message","缺少参数");
        }else{
            try {
                if (pageNum == null || pageNum.length() == 0) {
                    pageNum = "1";
                }
                page.setPd(pd);
                page.setShowCount(10);
                page.setCurrentPage(Integer.parseInt(pageNum));
                List<PageData> list=rechargeService.datalistPage(page);
                Map<String, Object> map = new HashedMap();
                if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                    map.put("data",list);
                }else{
                    message="没有内容了!";
                    map.put("message",message);
                    map.put("data",new ArrayList());
                }
                pd.clear();
                pd.put("object",map);
                pd.put("code","1");
                pd.put("message",message);
                pd.put("pageTotal",String.valueOf(page.getTotalPage()));
            }catch (Exception e){
                pd.clear();
                pd.put("code","2");
                pd.put("message","程序出错,请联系管理员!");
                e.printStackTrace();
            }
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

    /**
     *
     * @param uid 用户ID
     * @param store_id 店铺ID
     * @param count 数量
     * @param money 金钱
     * @return
     */
    @RequestMapping(value = "saveRecharge",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveRecharge(String uid,String store_id,String count,String money,String date,String dateString,String securityString ){
        logBefore(logger,"充值成功");
        PageData pd=this.getPageData();
        String SuiJi="knowledgeIsPower"+dateString;
        String Md5= MD5.md5(SuiJi);
        if(!Md5.equals(securityString)){
            pd.clear();
            pd.put("code","2");
            pd.put("message","你到底是谁!!");
        }else{
            if(store_id==null||store_id.length()==0||count==null||count.length()==0){
                pd.clear();
                pd.put("code","2");
                pd.put("message","缺少参数");
            }else{
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 =sdf.parse(dateString);
                    Date date2 =sdf.parse(DateUtil.getTime());
                    long delta = (date2.getTime() - date1.getTime()) / 1000;
                    if(delta>180){
                        pd.clear();
                        pd.put("code","2");
                        pd.put("message","你到底是谁!!");
                    }else{
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                        String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
                        Date now = new Date();
                        String no = s + sdf1.format(now).substring(2); // 订单号
                        pd.put("date", DateUtil.getTime());
                        pd.put("status","1");
                        pd.put("out_trade_no",no);
                        rechargeService.save(pd);
                        storeService.updateJiaNumber(pd);
                        PageData pd_s=storeService.findById(pd);
                        pd.clear();
                        pd.put("code","1");
                        pd.put("message","正确返回数据!");
                        pd.put("count",pd_s.get("number").toString());
                    }
                }catch (Exception e){
                    pd.clear();
                    pd.put("code","2");
                    pd.put("message","程序出错,请联系管理员!");
                    e.printStackTrace();
                }
            }
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
