package com.mtnz.controller.app.balance;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.service.system.balance.BalanceService;
import com.mtnz.service.system.customer.CustomerService;
import com.mtnz.service.system.integral.IntegralService;
import com.mtnz.service.system.order_info.OrderInfoService;
import com.mtnz.service.system.order_pro.OrderProService;
import com.mtnz.service.system.sys_app_user.SysAppUserService;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户账户
 */
@Controller
@RequestMapping(value = "app/balance",produces = "text/html;charset=UTF-8")
public class AppBalanceController extends BaseController{

    @Resource(name = "balanceService")
    private BalanceService balanceService;
    @Resource(name = "sysAppUserService")
    private SysAppUserService sysAppUserService;
    @Resource(name = "orderInfoService")
    private OrderInfoService orderInfoService;
    @Resource(name = "orderProService")
    private OrderProService orderProService;
    @Resource(name = "customerService")
    private CustomerService customerService;

    /**
     * 查询用户预付款余额
     * @param user_id
     * @return
     */
    @RequestMapping(value = "findUserbalance",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUserbalance(Long user_id){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{
            PageData pageData = balanceService.findUserbalanceByUserId(pd);
            if(pageData==null){
                PageData user = new PageData();
                user.put("customer_id",user_id);
                PageData userpage = customerService.findById(user);
                PageData userinto = new PageData();
                userinto.put("name",userpage.get("name"));
                userinto.put("balance",new BigDecimal(0));
                userinto.put("user_id",user_id);
                balanceService.saveBalance(userinto);
                pageData = balanceService.findUserbalanceByUserId(pd);
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",pageData);
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

    /**
     * 余额充值
     * @param balance 充值金额
     * @param user_id  用户id
     * @param open_id  开单人id
     * @Param deduction 扣除金额
     * @return
     */
    @RequestMapping(value = "saveUserbalance",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveUserbalance(BigDecimal balance,Long user_id,Long open_id,String remark,BigDecimal deduction){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{
            pd.put("origin_balance",balance.add(deduction));
            pd.put("type",2);
            balanceService.saveBalanceDetail(pd);
            balanceService.editBalanceByUserIdUp(pd);
            pd.put("owe",deduction);
            pd.put("customer_id",user_id);
            customerService.editOwnByCustomerIdDown(pd);
            pd.clear();
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

    /**
     * 查询充值明细
     * @param id
     * @return
     */
    @RequestMapping(value = "findBalanceDetail",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findBalanceDetail(Long id){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{
            PageData pageData = balanceService.findBalanceDetailById(pd);
            if(pageData!=null){
                PageData userid = new PageData();
                userid.put("uid",pageData.get("open_id"));
                PageData sysopen = sysAppUserService.findById(userid);
                pageData.put("open_name",sysopen.get("name"));
                if(pageData.get("back_id")!=null){
                    userid.put("uid",pageData.get("back_id"));
                    PageData sysback = sysAppUserService.findById(userid);
                    pageData.put("back_name",sysback.get("name"));
                }
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",pageData);
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


    /**
     * 查询充值列表
     * @param user_id
     * @return
     */
    @RequestMapping(value = "findUserbalanceByUserId",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveUserbalance(Long user_id){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{
            List<PageData> pageData = balanceService.findUserbalanceDetailByUserId(pd);
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",pageData);
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

    /**
     * 撤销充值单
     * @param id 详情id
     * @return
     */
    @RequestMapping(value = "returnBalance",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String returnBalance(Long id,Long back_id){
        logBefore(logger,"撤销充值单");
        PageData pd=this.getPageData();
        try{
            balanceService.editBalanceByUserId(pd);
            PageData pageData = balanceService.findBalanceDetailById(pd);
            PageData uppage = new PageData();
            uppage.put("balance",pageData.get("balance"));
            uppage.put("user_id",pageData.get("user_id"));
            balanceService.editBalanceByUserIdDown(uppage);
            if(new BigDecimal(pageData.get("balance").toString()).compareTo(new BigDecimal(pageData.get("origin_balance").toString()))==-1){
                BigDecimal cx = new BigDecimal(pageData.get("origin_balance").toString()).subtract(new BigDecimal(pageData.get("balance").toString()));
                PageData p2 = new PageData();
                p2.put("customer_id",pageData.get("user_id"));
                p2.put("owe",cx);
                customerService.editOwnByCustomerIdUp(p2);
            }
            pd.clear();
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

    /**
     * 查询欠款单列表
     * @param user_id
     * @return
     */
    @RequestMapping(value = "findorderbalance",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findorderbalance(Long user_id){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{

            pd.clear();
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


    /**
     * 查询代还款订单列表
     * @param customer_id
     * @return
     */
    @RequestMapping(value = "findOwnOrder",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String customer(Long customer_id){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{
            List<PageData> list = orderInfoService.findOwnOrder(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> pro = orderProService.findOrderProList(list.get(i));
                list.get(i).put("orderpro",pro);
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",list);
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
