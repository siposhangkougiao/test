package com.mtnz.controller.app.integral;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.already.AlreadyService;
import com.mtnz.service.system.customer.CustomerService;
import com.mtnz.service.system.integral.IntegralService;
import com.mtnz.service.system.person.PersonService;
import com.mtnz.service.system.recharge.RechargeService;
import com.mtnz.service.system.shorts.ShortService;
import com.mtnz.service.system.store.StoreService;
import com.mtnz.service.system.sys_app_user.SysAppUserService;
import com.mtnz.service.system.user.UserService;
import com.mtnz.util.*;
import com.mtnz.util.utils.GetWxOrderno;
import com.mtnz.util.utils.RequestHandler;
import com.mtnz.util.utils.TenpayUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "app/integral",produces = "text/html;charset=UTF-8")
public class AppIntegralController extends BaseController{

    @Resource(name = "integralService")
    private IntegralService integralService;

    @Resource(name = "sysAppUserService")
    private SysAppUserService sysAppUserService;


    /**
     * 积分管理页面查询
     * @param user_id  用户id
     * @return
     */
    @RequestMapping(value = "findUserIntegral",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUserIntegral(Long user_id){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{
            List<PageData> list  = integralService.findUserIntegral(pd);
            if(list.size()<1){
                PageData pageData = new PageData();
                pageData.put("remain_integral",new BigDecimal(0));
                pageData.put("all_integral",new BigDecimal(0));
                pageData.put("use_integral",new BigDecimal(0));
                list.add(pageData);
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

    /**
     * 积分明细 列表
     * @param user_id
     * @return
     */
    @RequestMapping(value = "findUserIntegrallist",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUserIntegrallist(Long user_id){
        logBefore(logger,"积分管理页面查询");
        PageData pd=this.getPageData();
        try{
            List<PageData> list  = integralService.findUserIntegrallist(pd);
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

    /**
     * 查询积分详情
     * @param id  积分列表里的id
     * @return
     */
    @RequestMapping(value = "findIntegralDetail",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findIntegralDetail(Long id){
        logBefore(logger,"积分详情");
        PageData pd=this.getPageData();
        try{
            PageData pageData  = integralService.findIntegralDetail(pd);
            if(pageData.get("open_user")!=null){
                PageData pageData1 = new PageData();
                pageData1.put("uid",pageData.get("open_user"));
                PageData openId = sysAppUserService.findById(pageData1);
                if(openId.get("name")!=null){
                    pageData.put("open_name",openId.get("name"));
                }
            }
            if(pageData.get("back_user")!=null){
                PageData pageData1 = new PageData();
                pageData1.put("uid",pageData.get("back_user"));
                PageData openId = sysAppUserService.findById(pageData1);
                if(openId.get("name")!=null){
                    pageData.put("back_name",openId.get("name"));
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
     * 查询积分比例设置
     * @param user_id
     * @return
     */
    @RequestMapping(value = "findIntegralSetup",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findIntegralSetup(Long user_id){
        logBefore(logger,"积分比例设置");
        PageData pd=this.getPageData();
        try{
            PageData pageData  = integralService.findIntegralSetup(pd);
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
     * 添加修改积分比例设置
     * @param user_id 用户id
     * @param number  设置的积分值
     * @return
     */
    @RequestMapping(value = "saveIntegralSetup",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveIntegralSetup(Long user_id, BigDecimal number){
        logBefore(logger,"修改积分设置");
        PageData pd=this.getPageData();
        try{
            integralService.deleteIntegralSetup(pd);
            integralService.saveIntegralSetup(pd);
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
     * 撤销积分
     * @param id
     * @return
     */
    @RequestMapping(value = "rebackIntegral",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String rebackIntegral(Long id,Long user_id){
        logBefore(logger,"积分撤单");
        PageData pd=this.getPageData();
        try{
            PageData deId = new PageData();
            deId.put("id",id);
            deId.put("undo",1);
            deId.put("back_user",user_id);
            integralService.editIntegralDetailById(deId);//修改撤销状态

            PageData pageData = integralService.findIntegralDetailById(id);//查询积分明细
            PageData page = new PageData();
            page.put("user_id",pageData.get("user_id"));
            page.put("customer_id",pageData.get("user_id"));
            page.put("integral",pageData.get("integral"));
            if(Integer.valueOf(pageData.get("status").toString())==1){//需要加
                integralService.editIntegral(page);
            }else {//需要减
                integralService.editIntegralUser(page);
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
     *  积分添加减少
     * @param user_id 被添加人id
     * @param open_id 操作人id
     * @param integral  操作的积分数量
     * @param status 1 兑换  2增加
     * @param name  客户名称
     * @return
     */
    @RequestMapping(value = "undoIntegral",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String undoIntegral(Long user_id,Long open_id,BigDecimal integral,Integer status,String name,String remark){
        logBefore(logger,"积分添加减少");
        PageData pd=this.getPageData();
        try{
            pd.put("customer_id",user_id);
            pd.put("open_user",open_id);
            integralService.saveIntegralDetail(pd);//添加订单详情
            PageData pdx = new PageData();
            pdx.put("customer_id",user_id);
            pdx.put("integral",integral);
            pdx.put("user_id",user_id);
            Integer count = integralService.findIntegralUserById(pdx);
            if(count==null ||count <1){
                PageData pageData = new PageData();
                pageData.put("user_id",user_id);
                pageData.put("remain_integral",new BigDecimal(0));
                pageData.put("all_integral",new BigDecimal(0));
                pageData.put("use_integral",new BigDecimal(0));
                pageData.put("name",name);
                integralService.saveIntegralUser(pageData);
            }
            if(status==1){//减少
                integralService.editIntegralUser(pdx);
            }else {//添加积分
                integralService.editIntegral(pdx);
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

}
