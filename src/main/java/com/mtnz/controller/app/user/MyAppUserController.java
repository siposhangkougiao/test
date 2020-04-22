package com.mtnz.controller.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.base.BaseController;
import com.mtnz.service.system.adminrelation.AdminRelationService;
import com.mtnz.service.system.balance.BalanceService;
import com.mtnz.service.system.customer.CustomerService;
import com.mtnz.service.system.integral.IntegralService;
import com.mtnz.service.system.order_info.OrderInfoService;
import com.mtnz.service.system.order_pro.OrderProService;
import com.mtnz.service.system.preorder.PreOrderService;
import com.mtnz.service.system.product.ProductService;
import com.mtnz.service.system.store.MyStoreService;
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
    @Resource(name = "productService")
    private ProductService productService;
    @Resource(name = "orderProService")
    private OrderProService orderProService;
    @Resource(name = "myStoreService")
    private MyStoreService myStoreService;
    @Resource
    private PreOrderService preOrderService;
    @Resource(name = "balanceService")
    private BalanceService balanceService;

    /**
     * 查询员工销售排名
     * @param store_id
     * @Param type 1日  2近七天 3进一月 4自定义
     * @return
     */
    @RequestMapping(value = "findMyStoreUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findMyStoreUser(Long store_id,String start_time,String end_time,Integer type,Long uid) {
        logBefore(logger, "查询员工销售排名",this.getPageData());
        PageData pd = this.getPageData();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            List<PageData> list = orderInfoService.findorderByOpenBillOnlyInfo(user);
            //总销售金额
            BigDecimal total_sale = new BigDecimal(0);
            //总欠款
            BigDecimal total_owe = new BigDecimal(0);
            //单品总金额
            BigDecimal total_one = new BigDecimal(0);
            //预支付金额
            BigDecimal pre_price = new BigDecimal(0);
            //账户抵扣的钱
            BigDecimal order_balance = new BigDecimal(0);
            //销售总提成
            BigDecimal total_level = new BigDecimal(0);
            //单品总提成
            BigDecimal one_levle= new BigDecimal(0);

            for (int i = 0; i < list.size(); i++) {
                total_level =total_level.add(new BigDecimal( list.get(i).get("total_sale").toString()));
                one_levle =one_levle.add(new BigDecimal( list.get(i).get("product_sale").toString()));
                total_owe = total_owe.add(new BigDecimal( list.get(i).get("owe_money").toString()));
                List<PageData> details = orderProService.findorderByOpenBillDetail(list.get(i));
                BigDecimal sub_total_level = new BigDecimal(0);
                for (int j = 0; j < details.size(); j++) {
                    //商品售价
                    BigDecimal product_price = new BigDecimal( details.get(j).get("product_price").toString());
                    //商品规格
                    BigDecimal norms1 = new BigDecimal( details.get(j).get("norms1").toString());
                    //本单整袋销量
                    BigDecimal order_kuncun = new BigDecimal(details.get(j).get("order_kuncun").toString()).multiply(product_price);
                    //本单零售销量
                    BigDecimal now_number = new BigDecimal(details.get(j).get("now_number").toString()).divide(norms1,4,BigDecimal.ROUND_HALF_UP).multiply(product_price);
                    //销售总价
                    total_sale = total_sale.add(order_kuncun).add(now_number);
                    PageData dataone = new PageData();
                    dataone.put("uid",uid);
                    dataone.put("product_id",details.get(j).get("product_id"));
                    PageData product = productService.findSaleLevel(dataone);
                    if(product!=null){
                        BigDecimal level = new BigDecimal(product.get("level").toString());
                        /*BigDecimal one_price = product_price.multiply(level);
                        details.get(j).put("one_price",one_price);//单个提成*/
                        if(order_kuncun.compareTo(new BigDecimal(0))==1){
                            BigDecimal more_price = level.multiply(order_kuncun);
                            details.get(j).put("more_price",more_price);//单个提成
                            total_one = total_one.add(more_price);
                            sub_total_level = sub_total_level.add(more_price);
                        }else if(now_number.compareTo(new BigDecimal(0))==1){
                            BigDecimal more_price = level.multiply(now_number);
                            details.get(j).put("more_price",more_price);//单个提成
                            total_one = total_one.add(more_price);
                            sub_total_level = sub_total_level.add(more_price);
                        }
                    }
                }
                /*list.get(i).put("sub_total_level",sub_total_level);*/
                list.get(i).put("sub_total_level",list.get(i).get("product_sale").toString());
                list.get(i).put("details",details);
                PreOrder preOrder = preOrderService.selectPreOrderByOrderInfo(list.get(i));
                if(preOrder!=null){
                    list.get(i).put("pre_price",preOrder.getPrice());
                    pre_price =pre_price.add(preOrder.getPrice());
                }else {
                    list.get(i).put("pre_price",0);
                }
                PageData pbalance = balanceService.findBalanceDetailByOrderId(list.get(i));
                if(pbalance!=null){
                    list.get(i).put("order_balance",pbalance.get("balance"));
                    order_balance = order_balance.add(new BigDecimal(pbalance.get("balance").toString()));
                }else {
                    list.get(i).put("order_balance",0);
                }
            }
            /*BigDecimal total_level = new BigDecimal(0);
            PageData pageData = productService.selectSaleLevel(pd);
            if(pageData!=null){
                total_level = total_sale.multiply(new BigDecimal(pageData.get("level").toString()));
            }*/
            user.put("total_sale",total_sale);//总销售额
            user.put("total_owe",total_owe);//欠款
            user.put("total_one",one_levle);//单品总提成
            user.put("total_level",total_level);//销售总提成
            user.put("pre_price",pre_price);//总预支付的钱
            user.put("order_balance",order_balance);//总余额抵扣的钱
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

    /**
     * 设置店员比例
     * @param uid
     * @param type
     * @param level
     * @param product_id
     * @return
     */
    @RequestMapping(value = "setlevel", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String setlevel(Long uid,Integer type,BigDecimal level,Long product_id) {
        logBefore(logger, "设置店员比例");
        PageData pd = this.getPageData();
        try {
            sysAppUserService.saveLevel(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
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
     * 查询商品对应的员工比例
     * @param pageNumber
     * @param pageSize
     * @param type
     * @param store_id
     * @return
     */
    @RequestMapping(value = "selectProductLevel", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String selectProductLevel(Integer pageNumber,Integer pageSize,Integer type,Long store_id,Long uid,String product_name) {
        logBefore(logger, "查询商品列表和对应的比例");
        PageData pd = this.getPageData();
        try {
            if(pageNumber==null||pageSize==null){
                pageNumber = getPageNumber();
                pageSize = getPageSize();
            }
            pd.put("pageNumber",pageNumber);
            pd.put("pageSize",pageSize);
            PageHelper.startPage(pageNumber,pageSize);
            List<PageData> list=productService.selectProductLevel(pd);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("uid",uid);
                PageData level = productService.selectLevel(list.get(i));
                if(level!=null){
                    list.get(i).put("level",new BigDecimal(level.get("level").toString()));
                }else {
                    list.get(i).put("level",new BigDecimal(0));
                }
            }
            PageInfo<PageData> pageInfo = new PageInfo<>(list);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data",list);
            pd.put("pageTotal",pageInfo.getTotal());
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

    /**查询员工的总销售比例
     * @param uid
     * @return
     */
    @RequestMapping(value = "selectSaleLevel", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String selectSaleLevel(Long uid) {
        logBefore(logger, "查询员工对应的总销售比例");
        PageData pd = this.getPageData();
        try {
            PageData pageData = productService.selectSaleLevel(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            if(pageData==null){
                PageData pds = new PageData();
                pds.put("level",new BigDecimal(0));
                pd.put("data",pds);
                pd.put("level",new BigDecimal(0));
            }else {
                pd.put("data",pageData);
                pd.put("level",new BigDecimal(pageData.get("level").toString()));
            }
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
     * 添加店员账号获取注册验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "getcode",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getcode(String phone){
        logBefore(logger,"获取短信验证码");
        PageData pd=this.getPageData();
        try{
            pd.put("username",phone);
            PageData pd_u=sysAppUserService.findUserName(pd);
            if(pd_u!=null){
                return getMessage("-101","用户已存在，不能重复注册！");
            }
            Map<String, Object> map = new HashMap();
            String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
            System.out.println("验证码为：" + yzm);
            SmsBao sms = new SmsBao();
            String context = "店主正在给您开通店员权限,验证码：" + yzm;
            String result = sms.sendSMS(phone, context);
            PageData pd2 = new PageData();
            pd2.put("code", yzm);
            pd2.put("phone", phone);
            pd2.put("start_time", DateUtil.getTime());
            Calendar afterTime = Calendar.getInstance();
            afterTime.add(Calendar.MINUTE, 5); // 当前分钟+5
            Date afterDate = (Date) afterTime.getTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(">>>>>>>>>>>>>"+sdf.format(afterDate));
            pd2.put("end_time", afterDate);
            pd2.put("now_time", new Date());
            PageData codepage = myStoreService.findyzm(pd2);
            if(codepage!=null){
                return getMessage("2","验证码已发送,五分钟后重试");
            }
            myStoreService.saveyzm(pd2);
            if ("0".equals(result.split(",")[0])) {
                map.put("YZM", yzm);
            } else {
                map.put("YZM", "-1");
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",map);
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
     * 启用店员账号获取验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "getStatusCode",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getStatusCode(String phone){
        logBefore(logger,"获取短信验证码");
        PageData pd=this.getPageData();
        try{
            pd.put("username",phone);
            Map<String, Object> map = new HashMap();
            String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
            System.out.println("验证码为：" + yzm);
            SmsBao sms = new SmsBao();
            String context = "店主正在给您开通店员权限,验证码：" + yzm;
            String result = sms.sendSMS(phone, context);
            PageData pd2 = new PageData();
            pd2.put("code", yzm);
            pd2.put("phone", phone);
            pd2.put("start_time", DateUtil.getTime());
            Calendar afterTime = Calendar.getInstance();
            afterTime.add(Calendar.MINUTE, 5); // 当前分钟+5
            Date afterDate = (Date) afterTime.getTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(">>>>>>>>>>>>>"+sdf.format(afterDate));
            pd2.put("end_time", afterDate);
            pd2.put("now_time", new Date());
            PageData codepage = myStoreService.findyzm(pd2);
            if(codepage!=null){
                return getMessage("2","验证码已发送,五分钟后重试");
            }
            myStoreService.saveyzm(pd2);
            if ("0".equals(result.split(",")[0])) {
                map.put("YZM", yzm);
            } else {
                map.put("YZM", "-1");
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",map);
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
