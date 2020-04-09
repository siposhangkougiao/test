package com.mtnz.controller.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.service.system.adminrelation.AdminRelationService;
import com.mtnz.service.system.customer.CustomerService;
import com.mtnz.service.system.integral.IntegralService;
import com.mtnz.service.system.order_info.OrderInfoService;
import com.mtnz.service.system.store.StoreService;
import com.mtnz.service.system.sys_app_user.SysAppUserService;
import com.mtnz.service.system.yzm.YzmService;
import com.mtnz.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;



@Controller
@RequestMapping(value = "app/myuser", produces = "text/html;charset=UTF-8")
public class MyAppUserController extends BaseController {


    @Resource(name = "sysAppUserService")
    private SysAppUserService sysAppUserService;    //用户
    @Resource(name = "storeService")
    private StoreService storeService;
    @Resource(name = "yzmService")
    private YzmService yzmService;
    @Resource(name = "adminRelationService")
    private AdminRelationService adminRelationService;
    @Resource(name = "customerService")
    private CustomerService customerService;
    @Resource(name = "integralService")
    private IntegralService integralService;
    @Resource(name = "orderInfoService")
    private OrderInfoService orderInfoService;

    /**
     * 查询员工销售排名
     * @param store_id
     * @Param type 1日  2近七天 3进一月 4自定义
     * @return
     */
    @RequestMapping(value = "findMyStoreUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findMyStoreUser(Long store_id,String start_time,String end_time,Integer type) {
        logBefore(logger, "查询员工销售排名");
        PageData pd = this.getPageData();
        try {
            List<PageData> pd_a = sysAppUserService.findUserByStore(pd);
            for (int i = 0; i < pd_a.size(); i++) {
                PageData openUser = pd_a.get(i);
                if(type ==1){
                    openUser.put("start_time",MyTimesUtil.getStartTime());
                    openUser.put("end_time",MyTimesUtil.getEndTime());
                }else if(type ==2){
                    openUser.put("start_time",MyTimesUtil.getDaySevenRange().get("startDate"));
                    openUser.put("end_time",MyTimesUtil.getDaySevenRange().get("endDate"));
                }else if(type==3){
                    openUser.put("start_time",MyTimesUtil.getDayTRange().get("startDate"));
                    openUser.put("end_time",MyTimesUtil.getDayTRange().get("endDate"));
                }else if(type==4){
                    openUser.put("start_time",start_time);
                    openUser.put("end_time",end_time);
                }
                BigDecimal total_sale = new BigDecimal(0);//总销售额
                BigDecimal total_cost = new BigDecimal(0);//成本价总额
                BigDecimal total_preferential= new BigDecimal(0);//总优惠
                List<PageData> userlist = orderInfoService.findorderByOpenBill(openUser);
                Long order_id = 0L;
                for (int j = 0; j < userlist.size(); j++) {
                    //商品售价
                    BigDecimal product_price = new BigDecimal( userlist.get(j).get("product_price").toString());
                    //商品进货价
                    BigDecimal purchase_price = new BigDecimal( userlist.get(j).get("purchase_price").toString());
                    //商品规格
                    BigDecimal norms1 = new BigDecimal(userlist.get(j).get("norms1").toString());
                    //本单整袋销量
                    BigDecimal order_kuncun = new BigDecimal( userlist.get(j).get("order_kuncun").toString()).multiply(product_price);
                    //本单零售销量
                    BigDecimal now_number = new BigDecimal(userlist.get(j).get("now_number").toString()).divide(norms1,4,BigDecimal.ROUND_HALF_UP).multiply(product_price);
                    //销售总价
                    total_sale = total_sale.add(order_kuncun).add(now_number);
                    //本单整袋成本价
                    BigDecimal order_kuncun_br = new BigDecimal(userlist.get(j).get("order_kuncun").toString()).multiply(purchase_price);
                    //本单零售成本价
                    BigDecimal now_number_br = new BigDecimal(userlist.get(j).get("now_number").toString()).divide(norms1,4,BigDecimal.ROUND_HALF_UP).multiply(purchase_price);
                    total_cost = total_cost.add(order_kuncun_br).add(now_number_br);
                    Long now_order_id = (Long) userlist.get(j).get("order_info_id");
                    if(!order_id.equals(now_order_id)){
                        total_preferential = total_preferential.add(new BigDecimal(userlist.get(j).get("discount_money").toString()));
                    }
                }
                pd_a.get(i).put("total_sale",total_sale);
                pd_a.get(i).put("total_cost",total_cost);
                pd_a.get(i).put("total_profits",total_sale.subtract(total_cost).subtract(total_preferential));
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", pd_a);
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = "";
        try {
            str = mapper.writeValueAsString(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 查询员工销售记录
     * @param uid
     * @return
     */
    @RequestMapping(value = "findMyStoreUserDetail", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findMyStoreUserDetail(Long uid) {
        logBefore(logger, "查询员工销售记录");
        PageData pd = this.getPageData();
        try {
            //查询员工信息
            PageData user = sysAppUserService.findById(pd);
            user.put("start_time",MyTimesUtil.getDaySevenRange().get("startDate"));
            user.put("end_time",MyTimesUtil.getDaySevenRange().get("endDate"));
            List<PageData> list = orderInfoService.findorderByOpenBill(user);
            //总销售金额
            BigDecimal total_sale = new BigDecimal(0);
            //总欠款
            BigDecimal total_owe = new BigDecimal(0);
            Long order_id = 0L;
            for (int i = 0; i < list.size(); i++) {
                //商品售价
                BigDecimal product_price = new BigDecimal( list.get(i).get("product_price").toString());
                //商品规格
                BigDecimal norms1 = new BigDecimal( list.get(i).get("norms1").toString());
                //本单整袋销量
                BigDecimal order_kuncun = new BigDecimal(list.get(i).get("order_kuncun").toString()).multiply(product_price);
                //本单零售销量
                BigDecimal now_number = new BigDecimal(list.get(i).get("now_number").toString()).divide(norms1,4,BigDecimal.ROUND_HALF_UP).multiply(product_price);
                //销售总价
                total_sale = total_sale.add(order_kuncun).add(now_number);
                Long now_order_id = (Long) list.get(i).get("order_info_id");
                if(!order_id.equals(now_order_id)){
                    total_owe = total_owe.add(new BigDecimal( list.get(i).get("owe_money").toString()));
                    order_id = now_order_id;
                }
            }
            user.put("total_sale",total_sale);
            user.put("total_owe",total_owe);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("user",user);
            pd.put("list",list);
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = "";
        try {
            str = mapper.writeValueAsString(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

}
