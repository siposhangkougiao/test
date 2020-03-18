package com.mtnz.service.system.order_kuncun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtnz.controller.base.BaseController;
import com.mtnz.dao.DaoSupport;
import com.mtnz.service.system.agency.AgencyService;
import com.mtnz.service.system.customer.CustomerService;
import com.mtnz.service.system.order_info.OrderInfoService;
import com.mtnz.service.system.order_pro.OrderProService;
import com.mtnz.service.system.product.KunCunService;
import com.mtnz.service.system.product.ProductService;
import com.mtnz.service.system.sys_app_user.SysAppUserService;
import com.mtnz.util.DateUtil;
import com.mtnz.util.PageData;
import org.codehaus.jackson.map.Serializers;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
    Created by xxj on 2018\6\12 0012.  
 */
@Service
@Resource(name = "orderKuncunService")
public class OrderKuncunService extends BaseController{
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;
    @Resource(name = "orderInfoService")
    private OrderInfoService orderInfoService;
    @Resource(name = "customerService")
    private CustomerService customerService;
    @Resource(name = "orderProService")
    private OrderProService orderProService;
    @Resource(name = "kunCunService")
    private KunCunService kunCunService;
    @Resource(name = "sysAppUserService")
    private SysAppUserService sysAppUserService;
    @Resource(name = "agencyService")
    private AgencyService agencyService;
    @Resource(name = "productService")
    private ProductService productService;


    public void save(PageData pd) throws Exception {
        daoSupport.save("OrderKunCunMapper.save",pd);
    }


    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderKunCunMapper.findList",pd);
    }

    public void editNums(PageData pd) throws Exception {
        daoSupport.update("OrderKunCunMapper.editNums",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderKunCunMapper.findById",pd);
    }

    public PageData findSupplierProduct(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderKunCunMapper.findSupplierProduct",pd);
    }

    public List<PageData> findOrderIdList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderKunCunMapper.findOrderIdList",pd);
    }

    public void editrevokes(PageData pd) throws Exception {
        daoSupport.update("OrderKunCunMapper.editrevokes",pd);
    }

    public List<PageData> findOrderProList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderKunCunMapper.findOrderProList",pd);
    }

    public PageData findSumProduct(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderKunCunMapper.findSumProduct",pd);
    }



    public String saveOrder(String name,String phone,String status,
                            String total_money,String money,String discount_money,
                            String owe_money,String data,String customer_id,String store_id,
                            String medication_date,String remarks,String uid,String open_bill ,String date,Integer isli) throws Exception {
        logBefore(logger,"销售开单");
        java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
        PageData pd=this.getPageData();
        ObjectMapper mapper=new ObjectMapper();
        if(name==null||name.length()==0||status==null||status.length()==0){
            pd.clear();
            pd.put("code","2");
            pd.put("message","缺少参数");
        }else{
                PageData pd_customer=new PageData();//存放的是客户的信息
                String statuss="0";
                if(customer_id.length()==0){
                    //判断客户存在不存在
                    pd_customer=customerService.findNameCustomer(pd);
                    if(pd_customer!=null){
                        customer_id=pd_customer.get("customer_id").toString();
                    }else{//如果不存在的话就添加一个客户
                        pd_customer=new PageData();
                        pd_customer.put("name",name);
                        pd_customer.put("phone",phone);
                        pd_customer.put("address","");
                        pd_customer.put("crop","");
                        pd_customer.put("area","");
                        pd_customer.put("input_date", date);
                        pd_customer.put("billing_date","");
                        pd_customer.put("owe","0");
                        pd_customer.put("status","0");
                        pd_customer.put("store_id",store_id);
                        pd_customer.put("uid",uid);
                        pd_customer.put("province","");
                        pd_customer.put("city","");
                        pd_customer.put("county","");
                        pd_customer.put("street","");
                        customerService.save(pd_customer);
                        customer_id=pd_customer.get("customer_id").toString();
                        statuss="1";
                    }
                }else{
                    pd_customer=customerService.findById(pd);//如果存在就查找出来客户
                }
                Date now = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
                // 订单号
                String no = s + sdf1.format(now).substring(2);
                PageData pd_o=new PageData();
                pd_o.put("store_id",store_id);
                pd_o.put("customer_id",customer_id);
                pd_o.put("name",name);
                pd_o.put("phone",phone);
                pd_o.put("address",pd_customer.getString("province")+pd_customer.getString("city")+
                        pd_customer.getString("province")+pd_customer.getString("county")+pd_customer.getString("street")+pd_customer.getString("address"));
                pd_o.put("money",money);
                pd_o.put("date",date);
                pd_o.put("status",status);
                pd_o.put("owe_money",owe_money);
                pd_o.put("total_money",total_money);
                pd_o.put("discount_money",discount_money);
                pd_o.put("medication_date",medication_date);
                pd_o.put("remarks",remarks);
                pd_o.put("order_number",no);
                pd_o.put("return_date","");
                if(open_bill!=null&&open_bill.length()!=0){
                    pd_o.put("open_bill",open_bill);
                }else{
                    pd_o.put("open_bill","");
                }
                orderInfoService.save(pd_o);//添加主表订单信息
                data = DateUtil.delHTMLTag(data);
                data = data.replace("\r", "");
                data = data.replace("\n", "");
                data = data.replace("\\", "");
                data = data.replace("\\\\", "");
                data = data.replace("/", "");
                data = data.replace(" ", "XINg");
                Gson gson = new Gson();
                List<PageData> list = gson.fromJson(data, new TypeToken<List<PageData>>() {
                }.getType());   //获取订单商品列表
                PageData pd_cc=new PageData();
                pd_cc.put("customer_id",customer_id);
                pd_cc.put("billing_date",pd_o.getString("date"));
                customerService.updateBillingDate(pd_cc);//这里更新用户的最新下单时间
                for (int i=0;i<list.size();i++){//遍历获取的商品信息
                    list.get(i).put("order_info_id",pd_o.get("order_info_id").toString());
                    list.get(i).put("orde_by","1");
                    orderProService.save(list.get(i));//添加商品详情表
                    list.get(i).put("product_id",(new Double(list.get(i).get("product_id").toString())).intValue());
                    list.get(i).put("store_id",store_id);
                    List<PageData> lists=kunCunService.findList(list.get(i));//查找库存
                    String kucun=list.get(i).get("num").toString();//商品购买的数量（这里是需要修改的）
                    if(lists!=null&&lists.size()!=0){//遍历库存
                        for (int j=0;j<lists.size();j++){
                            int in=(new Double(kucun)).intValue();
                            if(Integer.valueOf(lists.get(j).get("nums").toString())>=in){//现有库存大于实际购买的数量时
                                lists.get(j).put("order_pro_id",list.get(i).get("order_pro_id").toString());
                                lists.get(j).put("product_price",list.get(i).get("product_price").toString());
                                lists.get(j).put("purchase_price",lists.get(j).getString("purchase_price"));
                                lists.get(j).put("order_info_id",pd_o.get("order_info_id").toString());
                                lists.get(j).put("num",kucun);
                                lists.get(j).put("store_id",store_id);
                                lists.get(j).put("date",date);
                                save(lists.get(j));
                                kucun="0";
                                lists.get(j).put("nums",Integer.valueOf(lists.get(j).get("nums").toString())-Integer.valueOf(in));
                                kunCunService.editNum(lists.get(j));
                            }else{
                                if(j==lists.size()-1){
                                    lists.get(j).put("order_pro_id",list.get(i).get("order_pro_id").toString());
                                    lists.get(j).put("product_price",list.get(i).get("product_price").toString());
                                    lists.get(j).put("purchase_price",lists.get(j).getString("purchase_price"));
                                    lists.get(j).put("order_info_id",pd_o.get("order_info_id").toString());
                                    lists.get(j).put("num",kucun);
                                    lists.get(j).put("store_id",store_id);
                                    lists.get(j).put("date",date);
                                    save(lists.get(j));
                                    kucun="0";
                                    lists.get(j).put("nums","0");
                                    kunCunService.editNum(lists.get(j));
                                }else{
                                    lists.get(j).put("order_pro_id",list.get(i).get("order_pro_id").toString());
                                    lists.get(j).put("product_price",list.get(i).get("product_price").toString());
                                    lists.get(j).put("purchase_price",lists.get(j).getString("purchase_price"));
                                    lists.get(j).put("order_info_id",pd_o.get("order_info_id").toString());
                                    lists.get(j).put("store_id",store_id);
                                    lists.get(j).put("num",lists.get(j).get("nums").toString());
                                    lists.get(j).put("date",date);
                                    save(lists.get(j));
                                    Integer kuncuns=Integer.valueOf(in)-Integer.valueOf(lists.get(j).get("nums").toString());
                                    kucun=kuncuns.toString();
                                    lists.get(j).put("nums","0");
                                    kunCunService.editNum(lists.get(j));
                                }
                            }
                        }
                    }else{//如果商品没有库存信息
                        PageData pd_p=kunCunService.findByproduct_id(list.get(i));
                        if (pd_p!=null){
                            pd_p.put("order_pro_id",list.get(i).get("order_pro_id").toString());
                            pd_p.put("product_price",list.get(i).get("product_price").toString());
                            pd_p.put("purchase_price",pd_p.getString("purchase_price"));
                            pd_p.put("order_info_id",pd_o.get("order_info_id").toString());
                            pd_p.put("num",kucun);
                            pd_p.put("date",date);
                            save(pd_p);
                        }else{
                            PageData pd_pp=productService.findById(list.get(i));
                            pd_pp.put("order_pro_id",list.get(i).get("order_pro_id").toString());
                            pd_pp.put("product_price",pd_pp.get("product_price").toString());
                            if(pd_pp!=null&&!pd_pp.get("purchase_price").toString().equals("0")){
                                pd_pp.put("purchase_price",pd_pp.getString("purchase_price"));
                            }else{
                                pd_pp.put("purchase_price",list.get(i).get("product_price").toString());
                            }
                            pd_pp.put("kuncun_id","0");
                            pd_pp.put("store_id",store_id);
                            pd_pp.put("order_info_id",pd_o.get("order_info_id").toString());
                            pd_pp.put("num",kucun);
                            pd_pp.put("date",date);
                            save(pd_pp);//添加订单库存信息
                        }
                    }
                    productService.editNum(list.get(i));//更新商品库存信息
                }
                //添加库存记录
                kunCunService.batchSavess(list,store_id,DateUtil.getTime(),"0",customer_id,pd_o.get("order_info_id").toString(),"1",pd_o.get("order_info_id").toString());
                Double owe=0.0;
                if(status.equals("1")){// 状态 0实收款  1欠款
                    owe=Double.valueOf(pd_customer.get("owe").toString())+Double.valueOf(owe_money);
                    pd_customer.put("owe",df.format(owe));
                    customerService.updateOwe(pd_customer);//更新客户欠款信息
                }else{
                    owe=Double.valueOf(pd_customer.get("owe").toString());
                }
                if(medication_date!=""){
                    String[] moeication=medication_date.split(" ");
                    PageData pd_c=sysAppUserService.findById(pd);
                    pd.put("agency",moeication[0]+name+"用药.");
                    pd.put("month",moeication[0]);
                    pd.put("hour",moeication[1].split(":")[0]);
                    pd.put("name",name);
                    pd.put("openid",pd_c.getString("openid"));
                    pd.put("date", DateUtil.getTime());
                    pd.put("fstatus","0");
                    pd.put("customer_id",customer_id);
                    agencyService.save(pd);//添加代办实行，提醒下次用药时间
                }
                pd.clear();
                pd.put("code","1");
                pd.put("message","正确返回数据!");
                pd.put("status",statuss);
                if(owe>0){
                    pd.put("owe",df.format(owe));
                }else{
                    pd.put("owe","0.0");
                }
                pd.put("customer_id",customer_id);
                pd.put("order_number",no);
        }
        String str="";
        try {
            str= mapper.writeValueAsString(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public String saveOrders(String name,String phone,String status,
                             String total_money,String money,String discount_money,
                             String owe_money,String data,String customer_id,String store_id,
                             String medication_date,String remarks,String uid) throws Exception{
            BaseController.logBefore(logger,"销售开单");
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
            PageData pd=this.getPageData();
            ObjectMapper mapper=new ObjectMapper();
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            String no = s + sdf1.format(now).substring(2); // 订单号
            PageData pd_o=new PageData();
            pd_o.put("store_id",store_id);
            pd_o.put("customer_id",customer_id);
            pd_o.put("name",name);
            pd_o.put("phone",phone);
            pd_o.put("address","");
            pd_o.put("money",money);
            pd_o.put("date",DateUtil.getTime());
            pd_o.put("status",status);
            pd_o.put("owe_money",owe_money);
            pd_o.put("total_money",total_money);
            pd_o.put("discount_money",discount_money);
            pd_o.put("medication_date",medication_date);
            pd_o.put("remarks",remarks);
            pd_o.put("order_number",no);
            pd_o.put("return_date","");
            orderInfoService.save(pd_o);
            data = DateUtil.delHTMLTag(data);
            data = data.replace("\r", "");
            data = data.replace("\n", "");
            data = data.replace("\\", "");
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(data, new TypeToken<List<PageData>>() {
            }.getType());   //获取订单商品列表
            PageData pd_cc=new PageData();
            pd_cc.put("customer_id",customer_id);
            pd_cc.put("billing_date",pd_o.getString("date"));
            customerService.updateBillingDate(pd_cc);
            for(int i=0;i<list.size();i++){
                list.get(i).put("order_info_id",pd_o.get("order_info_id").toString());
                list.get(i).put("orde_by","1");
                orderProService.save(list.get(i));
                list.get(i).put("product_id",(new Double(list.get(i).get("product_id").toString())).intValue());
                list.get(i).put("store_id",store_id);
                List<PageData> lists=kunCunService.findList(list.get(i));
                String kucun=list.get(i).get("num").toString();
                if(list.get(i).getString("status").equals("0")){
                    for (int j=0;j<lists.size();j++){
                        int in=(new Double(kucun)).intValue();
                        if(Integer.valueOf(lists.get(j).get("nums").toString())>=in){
                            lists.get(j).put("order_pro_id",list.get(i).get("order_pro_id").toString());
                            lists.get(j).put("product_price",list.get(i).get("product_price").toString());
                            lists.get(j).put("purchase_price",lists.get(j).getString("purchase_price"));
                            lists.get(j).put("order_info_id",pd_o.get("order_info_id").toString());
                            lists.get(j).put("num",kucun);
                            lists.get(j).put("store_id",store_id);
                            save(lists.get(j));
                            kucun="0";
                            lists.get(j).put("nums",Integer.valueOf(lists.get(j).get("nums").toString())-Integer.valueOf(in));
                            kunCunService.editNum(lists.get(j));
                            continue;
                        }else{
                            if(j==lists.size()-1){
                                lists.get(j).put("order_pro_id",list.get(i).get("order_pro_id").toString());
                                lists.get(j).put("product_price",list.get(i).get("product_price").toString());
                                lists.get(j).put("purchase_price",lists.get(j).getString("purchase_price"));
                                lists.get(j).put("order_info_id",pd_o.get("order_info_id").toString());
                                lists.get(j).put("num",kucun);
                                lists.get(j).put("store_id",store_id);
                                save(lists.get(j));
                                kucun="0";
                                lists.get(j).put("nums","0");
                                kunCunService.editNum(lists.get(j));
                            }else{
                                lists.get(j).put("order_pro_id",list.get(i).get("order_pro_id").toString());
                                lists.get(j).put("product_price",list.get(i).get("product_price").toString());
                                lists.get(j).put("purchase_price",lists.get(j).getString("purchase_price"));
                                lists.get(j).put("order_info_id",pd_o.get("order_info_id").toString());
                                lists.get(j).put("store_id",store_id);
                                lists.get(j).put("num",lists.get(j).get("nums").toString());
                                save(lists.get(j));
                                Integer kuncuns=Integer.valueOf(in)-Integer.valueOf(lists.get(j).get("nums").toString());
                                kucun=kuncuns.toString();
                                lists.get(j).put("nums","0");
                                kunCunService.editNum(lists.get(j));
                            }
                        }
                    }
                }else{

                }
            }
        String str="";
        try {
            str= mapper.writeValueAsString(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }






}
