package com.mtnz.controller.app.already;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.already.AlreadyService;
import com.mtnz.service.system.customer.CustomerService;
import com.mtnz.util.DateUtil;
import com.mtnz.util.MD5;
import com.mtnz.util.PageData;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\3\22 0022.  
 */
@Controller
@RequestMapping(value = "app/already",produces = "text/html;charset=UTF-8")
public class AppAlreadyController extends BaseController{

    @Resource(name = "alreadyService")
    private AlreadyService alreadyService;
    @Resource(name = "customerService")
    private CustomerService customerService;

    /**
     *
     * @param money 钱数
     * @param customer_id 客户ID
     * @param store_id 店ID
     * @param discount 优惠
     * @return
     */
    @RequestMapping(value = "saveAlready",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveAlready(String money,String customer_id,String store_id,String dateString,String securityString,String discount){
        logBefore(logger,"还款");
        PageData pd=this.getPageData();
        if(money==null||money.length()==0||customer_id==null||customer_id.length()==0||
                store_id==null||store_id.length()==0){
            pd.clear();
            pd.put("code","2");
            pd.put("message","正确返回数据!");
        }else{
            try{
                String date=DateUtil.getTime();
                pd.put("date", date);
                pd.put("out_trade_no","");
                pd.put("status","1");
                if(discount==null||discount.length()==0){
                    pd.put("discount","0");
                }
                alreadyService.save(pd);
                PageData pd_c=customerService.findById(pd);
                if(discount!=null||discount.length()!=0){
                    pd_c.put("owe",Double.valueOf(pd_c.getString("owe"))-Double.valueOf(money)-Double.valueOf(discount));
                }else{
                    pd_c.put("owe",Double.valueOf(pd_c.getString("owe"))-Double.valueOf(money));
                }
                customerService.updateOwe(pd_c);
                String total_money="0";
                PageData pd_sum=alreadyService.findSumMoney(pd);
                if(pd_sum!=null){
                    total_money=pd_sum.get("moeny").toString();
                }
                pd.clear();
                pd.put("code","1");
                pd.put("message","正确返回数据!");
                pd.put("owe",pd_c.get("owe").toString());
                pd.put("total_money",total_money);
                pd.put("date",date);
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
     * @param customer_id 用户ID
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "findAlready",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAlready(String customer_id,String pageNum){
        logBefore(logger,"查询已还记录");
        PageData pd=this.getPageData();
        Page page=new Page();
        if(customer_id==null||customer_id.length()==0){
            pd.clear();
            pd.put("code","2");
            pd.put("message","缺少参数");
        }else{
            String message="正确返回数据!";
            try{
                if (pageNum == null || pageNum.length() == 0) {
                    pageNum = "1";
                }
                page.setPd(pd);
                page.setShowCount(10);
                page.setCurrentPage(Integer.parseInt(pageNum));
                List<PageData> list=alreadyService.datalistPage(page);
                Map<String, Object> map = new HashedMap();
                if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                    map.put("data",list);
                }else{
                    map.put("message",message);
                    map.put("data",new ArrayList());
                }
                pd.clear();
                pd.put("object",map);
                pd.put("code","1");
                pd.put("message",message);
                pd.put("pageTotal",String.valueOf(page.getTotalPage()));
            }catch (Exception e){
                e.printStackTrace();
                pd.clear();
                pd.put("code","2");
                pd.put("message","程序出错,请联系管理员");
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
