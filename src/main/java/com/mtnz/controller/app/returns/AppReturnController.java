package com.mtnz.controller.app.returns;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.order_info.OrderInfoService;
import com.mtnz.service.system.order_kuncun.OrderKuncunService;
import com.mtnz.service.system.order_pro.OrderProService;
import com.mtnz.service.system.product.KunCunService;
import com.mtnz.service.system.product.ProductService;
import com.mtnz.service.system.returns.ReturnOrderInfoService;
import com.mtnz.service.system.returns.ReturnOrderProService;
import com.mtnz.util.DateUtil;
import com.mtnz.util.PageData;
import com.mtnz.util.ReturnOrderExtend;
import com.mtnz.util.ReturnSupplierOrderExtend;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\5\30 0030.  
 */
@Controller
@RequestMapping(value = "app/return",produces = "text/html;charset=UTF-8")
public class AppReturnController extends BaseController{

    @Resource(name = "returnOrderInfoService")
    private ReturnOrderInfoService returnOrderInfoService;
    @Resource(name = "returnOrderProService")
    private ReturnOrderProService returnOrderProService;
    @Resource(name = "orderInfoService")
    private OrderInfoService orderInfoService;
    @Resource(name = "orderProService")
    private OrderProService orderProService;
    @Resource(name = "kunCunService")
    private KunCunService kunCunService;
    @Resource(name = "orderKuncunService")
    private OrderKuncunService orderKuncunService;
    @Resource(name = "productService")
    private ProductService productService;


    /**
     *
     * @param order_info_id 订单ID
     * @param data (order_pro_id 订单商品ID,product_name(商品名称),product_price(商品价格)
     *             ,num(数量),total(总价),norms1(规格1),norms2(规格2),norms3(规格3),product_id(商品ID))
     * @param money 总价
     * @param store_id 店ID
     * @param remarks 备注
     * @return
     */
    @RequestMapping(value = "returnOrderInfos",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String returnOrderInfos(String order_info_id,String data,String money,String store_id,
                                   String remarks,String open_bill){
        logBefore(logger,"订单退货");
        PageData pd=this.getPageData();
        try{
            //查询订单信息对象
            PageData pd_o=orderInfoService.findById(pd);
            if(remarks==null||remarks.length()==0){
                pd_o.put("remarks","");
            }else{
                pd_o.put("remarks",remarks);
            }
            if(open_bill==null||open_bill.length()==0){
                pd_o.put("open_bill","");
            }else{
                pd_o.put("open_bill",open_bill);
            }
            pd_o.put("total_money",money);
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            // 订单号
            String no = s + sdf1.format(now).substring(2);
            pd_o.put("order_number",no);
            pd_o.put("money",money);
            pd_o.put("return_date",DateUtil.getTime());
            pd_o.put("status","0");
            //添加退货信息对象
            returnOrderInfoService.save(pd_o);
            data = DateUtil.delHTMLTag(data);
            data = data.replace("\r", "");
            data = data.replace("\n", "");
            data = data.replace("\\", "");
            data = data.replace(" ", "XINg");
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(data, new TypeToken<List<PageData>>() {
            }.getType());   //获取订单商品列表
            for(int i=0;i<list.size();i++){
                //list.get(i).put("purchase_price",list.get(i).get("product_price").toString());
                list.get(i).put("return_order_info_id",pd_o.get("return_order_info_id").toString());
                list.get(i).put("order_info_id",order_info_id);
                list.get(i).put("order_pro_id",new Double(list.get(i).get("order_pro_id").toString()).intValue());
                list.get(i).put("product_id",new Double(list.get(i).get("product_id").toString()).intValue());
                //添加退货产品信息详情
                returnOrderProService.save(list.get(i));
                //查询订单库存的信息
                List<PageData> lists=orderKuncunService.findList(list.get(i));
                String kucun=list.get(i).get("num").toString();//需要退货的数量
                if(lists!=null&&lists.size()!=0){
                    for(int j=0;j<lists.size();j++){
                        int in=(new Double(kucun)).intValue();//需要退货的数量
                        //如果订单数量大于等于要退货的数量
                        if(Integer.valueOf(lists.get(j).get("nums").toString())>in){
                            lists.get(j).put("nums",Integer.valueOf(lists.get(j).get("nums").toString())-in);
                            //修改订单的库存
                            orderKuncunService.editNums(lists.get(j));
                            lists.get(j).put("nums",in);
                            //修改库存表的数据
                            kunCunService.editJiaNums(lists.get(j));
                            kucun="0";
                        }else{//全部退货
                            Integer kuncuns=in-Integer.valueOf(lists.get(j).get("nums").toString());
                            kucun=kuncuns.toString();
                            //修改库存表的数据
                            kunCunService.editJiaNums(lists.get(j));
                            lists.get(j).put("nums",0);
                            //修改订单的库存
                            orderKuncunService.editNums(lists.get(j));
                        }
                    }
                }
                //修改订单详情表数据
                orderProService.editOrderKuncun(list.get(i));
                //修改商品库存数量
                productService.editJiaNums(list.get(i));
            }
            //添加库存表记录  status=5表示退货加数量
            kunCunService.batchSaves(list,store_id,DateUtil.getTime(),"5","0",order_info_id,pd_o.get("return_order_info_id").toString());
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("order_number",no);
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
     *
     * @param order_info_id 订单ID
     * @param data (product_name(商品名称),product_price(商品价格)
     *             ,num(数量),total(总价),norms1(规格1),norms2(规格2),norms3(规格3),product_id(商品ID))
     * @param money 总价
     * @param store_id 店ID
     * @return
     */
    @RequestMapping(value = "returnOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String returnOrderInfo(String order_info_id,String data,String money,String store_id){
        logBefore(logger,"订单退货");
        PageData pd=this.getPageData();
        try{
            PageData pd_o=orderInfoService.findById(pd);
            pd_o.put("money",money);
            pd_o.put("return_date",DateUtil.getTime());
            returnOrderInfoService.save(pd_o);
            data = DateUtil.delHTMLTag(data);
            data = data.replace("\r", "");
            data = data.replace("\n", "");
            data = data.replace("\\", "");
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(data, new TypeToken<List<PageData>>() {
            }.getType());   //获取订单商品列表
            for(int i=0;i<list.size();i++){
                list.get(i).put("return_order_info_id",pd_o.get("return_order_info_id").toString());
                list.get(i).put("order_info_id",order_info_id);
                returnOrderProService.save(list.get(i));
            }
            kunCunService.batchSave(list,store_id,DateUtil.getTime(),"5",pd_o.get("customer_id").toString(),pd_o.get("return_order_info_id").toString(),"0");
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

    @RequestMapping(value = "findReturnOrderInfoId",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findReturnOrderInfoId(String return_order_info_id){
        logBefore(logger,"查询详情");
        PageData pd=this.getPageData();
        try{
            PageData pd_o=returnOrderInfoService.findById(pd);
            List<PageData> list=returnOrderProService.findList(pd_o);
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("order",pd_o);
            pd.put("list",list);
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

    @RequestMapping(value = "findReturnOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findReturnOrderInfo(String store_id,String pageNum,String startTime,
                                      String endTime,String customer_id,String name){
        logBefore(logger,"查询退货订单");
        PageData pd=this.getPageData();
        Page page=new Page();
        String message="正确返回数据!";
        try{
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("return_goods","0");
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list=returnOrderInfoService.datalistPage(page);
            for(int i=0;i<list.size();i++){
                List<PageData> list_pro=returnOrderProService.findList(list.get(i));
                list.get(i).put("order_pro",list_pro);
            }
            PageData pdMoney=returnOrderInfoService.findDateSumMoney(pd);
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
            String totalMoney="0.0";
            if(pdMoney!=null&&!pdMoney.get("money").toString().equals("0.0")){
                totalMoney=df.format(pdMoney.get("money"));
            }
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
            pd.put("totalMoney",totalMoney);
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


    @RequestMapping(value = "findlikeReturnOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findlikeReturnOrderInfo(String store_id,String name){
        logBefore(logger,"查询退货订单");
        PageData pd=this.getPageData();
        try{
            List<PageData> list=returnOrderInfoService.findLikeOrderInfo(pd);
            for(int i=0;i<list.size();i++){
                List<PageData> list_pro=orderProService.findList(list.get(i));
                list.get(i).put("order_pro",list_pro);
            }
            Map<String, Object> map = new HashedMap();
            map.put("data",list);
            pd.clear();
            pd.put("object",map);
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

    @RequestMapping(value = "revokesReturnOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String revokesReturnOrderInfo(String return_order_info_id){
        logBefore(logger,"撤销退货订单");
        PageData pd=this.getPageData();
        try{
            //查询退货单详情
            PageData pdReturn=returnOrderInfoService.findById(pd);
            pd.put("revokes","1");
            //修改退货单状态 1已撤销
            returnOrderInfoService.editRevokes(pd);
            List<PageData> list=returnOrderProService.findList(pd);
            for(int i=0;i<list.size();i++){
                list.get(i).put("order_info_id",pdReturn.get("order_info_id").toString());
                PageData pdOrderpro=orderProService.findOrderInfoProduct(list.get(i));
                List<PageData> listKun=orderKuncunService.findOrderProList(pdOrderpro);
                String kucun=list.get(i).get("num").toString();
                Integer aa = 0;
                for(int j=0;j<listKun.size();j++){
                    System.out.println("查询出来的库存id："+listKun.get(j).get("kuncun_id"));
                    int tuihuo=Integer.valueOf(listKun.get(j).get("num").toString())-Integer.valueOf(listKun.get(j).get("nums").toString());
                    if(tuihuo>=Integer.valueOf(kucun)){
                        listKun.get(j).put("nums",Integer.valueOf(listKun.get(j).get("nums").toString())+Integer.valueOf(kucun));
                        orderKuncunService.editNums(listKun.get(j));
                        if(aa==1){
                            System.out.println("走这里了");
                            listKun.get(j).put("nums",Integer.valueOf(kucun));
                        }else {
                            listKun.get(j).put("nums",Integer.valueOf(list.get(i).get("num").toString()));
                        }
                        System.out.println(">>>>>>>1111111"+listKun.get(j).get("nums"));
                        kunCunService.editJianNumss(listKun.get(j));
                        kucun="0";
                    }else{
                        listKun.get(j).put("nums",Integer.valueOf(listKun.get(j).get("nums").toString())+tuihuo);
                        orderKuncunService.editNums(listKun.get(j));
                        listKun.get(j).put("nums",tuihuo);
                        System.out.println(">>>>>>>222222"+listKun.get(j).get("nums"));
                        kunCunService.editJianNumss(listKun.get(j));
                        int kucuns=Integer.valueOf(kucun)-tuihuo;
                        kucun=String.valueOf(kucuns);
                        aa = 1;
                    }
                }
                pdOrderpro.put("nums",list.get(i).get("num").toString());
                orderProService.editOrderKuncuns(pdOrderpro);
                productService.editJianNums(pdOrderpro);
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

    @RequestMapping(value = "findReturnExcelUtil",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void findReturnExcelUtil(String store_id, HttpServletRequest request, HttpServletResponse response){
        try{
            PageData pd=this.getPageData();
            List<PageData> list=returnOrderProService.findQuanBu(pd);
            ReturnOrderExtend.printExcle(list,"E://ceshi.xls",request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
