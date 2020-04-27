package com.mtnz.controller.app.supplier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceOwe;
import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.order_kuncun.OrderKuncunService;
import com.mtnz.service.system.order_pro.OrderProService;
import com.mtnz.service.system.product.KunCunService;
import com.mtnz.service.system.product.ProductService;
import com.mtnz.service.system.repayments.RepaymentsService;
import com.mtnz.service.system.return_supplier.ReturnSupplierOrderInfoService;
import com.mtnz.service.system.return_supplier.ReturnSupplierOrderProService;
import com.mtnz.service.system.supplier.SupplierOrderInfoService;
import com.mtnz.service.system.supplier.SupplierOrderProService;
import com.mtnz.service.system.supplier.SupplierService;
import com.mtnz.sql.system.supplierbalance.SupplierBalanceOweMapper;
import com.mtnz.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\5\29 0029.  
 */
@Controller
@RequestMapping(value = "app/supplier",produces = "text/html;charset=UTF-8")
public class AppSupplierController extends BaseController{
    @Resource(name = "supplierService")
    private SupplierService supplierService;
    @Resource(name = "supplierOrderInfoService")
    private SupplierOrderInfoService supplierOrderInfoService;
    @Resource(name = "supplierOrderProService")
    private SupplierOrderProService supplierOrderProService;
    @Resource(name = "productService")
    private ProductService productService;
    @Resource(name = "kunCunService")
    private KunCunService kunCunService;
    @Resource(name = "orderProService")
    private OrderProService orderProService;
    @Resource(name = "orderKuncunService")
    private OrderKuncunService orderKuncunService;
    @Resource(name = "returnSupplierOrderInfoService")
    private ReturnSupplierOrderInfoService returnSupplierOrderInfoService;
    @Resource(name = "returnSupplierOrderProService")
    private ReturnSupplierOrderProService returnSupplierOrderProService;
    @Resource(name = "repaymentsService")
    private RepaymentsService repaymentsService;
    @Resource
    private SupplierBalanceOweMapper supplierBalanceOweMapper;


    /**
     *
     * @param store_id 店ID
     * @param name 联系人
     * @param gname 供应商名称
     * @param phone 手机号
     * @param province 省
     * @param city 市
     * @param county 县
     * @param street 街道
     * @param address 详细地址
     * @param remarks 备注
     * @param identity 身份证
     * @param img 图片
     * @param management_img 经营许可证
     * @return
     */
    @RequestMapping(value = "saveSupplier",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveSupplier(String store_id,String name,String phone,String province,String city,String county,
                               String street,String address,String remarks,String gname,String identity,String img,String management_img){
        logBefore(logger,"添加供应商");
        PageData pd=this.getPageData();
        try{
            pd.put("owe","0");
            pd.put("date", DateUtil.getTime());
            pd.put("billing_date","");
            if(identity==null||identity.length()==0){
                pd.put("identity","");
            }
            if(img!=null&&!"".equals(img)){
                String ffile1 = this.get32UUID() + ".jpg";
                String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "supplier/" + DateUtil.getDays(); // 文件上传路径
                File file = new File(filePath2, ffile1);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG +"supplier/"+DateUtil.getDays()+"/"+ ffile; // 文件上传路径
                boolean flag = ImageAnd64Binary.generateImage(img, filePath);
                img = Const.SERVERPATH + Const.FILEPATHIMG+"supplier/"+DateUtil.getDays()+"/"+ ffile;
            }else{
                img="";
            }
            if(management_img!=null&&!"".equals(management_img)){
                String ffile1 = this.get32UUID() + ".jpg";
                String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "management/" + DateUtil.getDays(); // 文件上传路径
                File file = new File(filePath2, ffile1);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG +"management/"+DateUtil.getDays()+"/"+ ffile; // 文件上传路径
                boolean flag = ImageAnd64Binary.generateImage(management_img, filePath);
                management_img = Const.SERVERPATH + Const.FILEPATHIMG+"management/"+DateUtil.getDays()+"/"+ ffile;
            }else{
                management_img="";
            }
            if(remarks!=null&&remarks.length()!=0){
                pd.put("remarks",remarks);
            }else{
                pd.put("remarks","");
            }
            pd.put("img",img);
            pd.put("management_img",management_img);
            supplierService.save(pd);
            PageData pd_s=supplierService.findById(pd);
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",pd_s);
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
     * @param store_id 店铺ID
     * @param pageNum 页数
     * @param state1 0不判断  1显示欠款大于0的
     * @param state2 0不判断  1显示预付款大于0的
     * @param state3 0不判断  1显示停用的供应商
     * @return
     */
    @RequestMapping(value = "findSupplier",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSupplier(String store_id,String pageNum,String state1,
                               String state2,String state3){
        logBefore(logger,"查询供应商");
        PageData pd=this.getPageData();
        Page page=new Page();
        java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
        if(store_id==null||store_id.length()==0){
            pd.clear();
            pd.put("code","2");
            pd.put("message","缺少参数!");
        }else{
            try {
                String message="正确返回数据!";
                if (pageNum == null || pageNum.length() == 0) {
                    pageNum = "1";
                }
                page.setPd(pd);
                page.setShowCount(100);
                page.setCurrentPage(Integer.parseInt(pageNum));
                List<PageData> list=supplierService.datalistPage(page);
                double total_money=0;
                BigDecimal axx = new BigDecimal(0);
                for(int i=0;i<list.size();i++){
                    PageData pd_c=productService.findCount(list.get(i));
                    list.get(i).put("count",pd_c.get("count").toString());
                    PageData pd_cc=supplierOrderInfoService.findSupplierCount(list.get(i));
                    list.get(i).put("count1",pd_cc.get("count").toString());
                    PageData pd_money=supplierOrderInfoService.findSumMoney(list.get(i));
                    if(pd_money!=null&&!pd_money.get("money").toString().equals("0.0")){
                        list.get(i).put("money",df.format(pd_money.get("money")));
                        total_money+=Double.valueOf(total_money)+Double.valueOf(pd_money.get("money").toString());
                        axx = axx.add(new BigDecimal(pd_money.get("money").toString()));
                    }else{
                        list.get(i).put("money","0.0");
                    }
                    PageData pd_ccs=orderKuncunService.findSupplierProduct(list.get(i));
                    if(pd_ccs!=null&&!pd_ccs.get("money").toString().equals("0.0")){
                        list.get(i).put("sale_money",df.format(pd_ccs.get("money")));
                    }else{
                        list.get(i).put("sale_money","0.0");
                    }
                    list.get(i).put("return_money","0.0");
                    SupplierBalanceOwe supplierBalanceOwe = new SupplierBalanceOwe();
                    supplierBalanceOwe.setSupplierId(Long.valueOf(list.get(i).get("supplier_id").toString()));
                    supplierBalanceOwe.setStoreId(Long.valueOf(list.get(i).get("store_id").toString()));
                    SupplierBalanceOwe BalanceOwe = supplierBalanceOweMapper.selectOne(supplierBalanceOwe);
                    if(BalanceOwe!=null){
                        list.get(i).put("prePrice",BalanceOwe.getPrePrice());
                    }else {
                        list.get(i).put("prePrice",new BigDecimal(0));
                    }

                }
                axx.setScale(2,BigDecimal.ROUND_HALF_UP);
                Map<String, Object> map = new HashedMap();
                if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                    map.put("data", list);
                }else{
                    map.put("message",message);
                    map.put("data",new ArrayList());
                }
                pd.clear();
                pd.put("object", map);
                pd.put("message", message);
                pd.put("code", "1");
                pd.put("pageTotal",String.valueOf(page.getTotalPage()));
                pd.put("total_money",axx);
            } catch (Exception e) {
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


    @RequestMapping(value = "findSupplierFeiXi",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSupplierFeiXi(String supplier_id){
        logBefore(logger,"供应商详情分析");
        PageData pd=this.getPageData();
        java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
        try{
            PageData pd_s=supplierService.findById(pd);
            PageData pd_cc=supplierOrderInfoService.findCount(pd);
            pd_s.put("count",pd_cc.get("count").toString());
            PageData pd_money=supplierOrderInfoService.findSumMoney(pd);
            if(pd_money!=null&&!pd_money.get("money").toString().equals("0.0")){
                pd_s.put("money",df.format(pd_money.get("money")));
            }else{
                pd_s.put("money","0.0");
            }
            pd_s.put("return_money","0.0");
            List<PageData> list=productService.findSupplierList(pd);
            PageData pd_mm=orderProService.findSum(pd_s);
            pd.clear();
            pd.put("product",list);
            pd.put("data",pd_s);
            pd.put("datas",pd_mm);
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


    @RequestMapping(value = "findlikeSupplier",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findlikeSupplier(String store_id,String name){
        logBefore(logger,"模糊查询供应商");
        PageData pd=this.getPageData();
        if(store_id==null||store_id.length()==0){
            pd.clear();
            pd.put("code","2");
            pd.put("message","缺少参数!");
        }else{
            try {
                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
                List<PageData> list=supplierService.findLikeSupplier(pd);
                for(int i=0;i<list.size();i++){
                    PageData pd_cc=supplierOrderInfoService.findSupplierCount(list.get(i));
                    list.get(i).put("count1",pd_cc.get("count").toString());
                }
                for(int i=0;i<list.size();i++){
                    PageData pd_c=productService.findCount(list.get(i));
                    list.get(i).put("count",pd_c.get("count").toString());
                    PageData pd_money=supplierOrderInfoService.findSumMoney(list.get(i));
                    if(pd_money!=null&&!pd_money.get("money").toString().equals("0.0")){
                        list.get(i).put("money",df.format(pd_money.get("money")));
                    }else{
                        list.get(i).put("money","0.0");
                    }

                    list.get(i).put("return_money","0.0");

                    SupplierBalanceOwe supplierBalanceOwe = new SupplierBalanceOwe();
                    supplierBalanceOwe.setSupplierId(Long.valueOf(list.get(i).getString("supplier_id")));
                    supplierBalanceOwe.setStoreId(Long.valueOf(list.get(i).getString("store_id")));
                    SupplierBalanceOwe BalanceOwe = supplierBalanceOweMapper.selectOneByExample(supplierBalanceOwe);
                    if(BalanceOwe!=null){
                        list.get(i).put("prePrice",BalanceOwe.getPrePrice());
                    }else {
                        list.get(i).put("prePrice",new BigDecimal(0));
                    }
                }
                Map<String, Object> map = new HashedMap();
                map.put("data", list);
                pd.clear();
                pd.put("object", map);
                pd.put("message", "正确返回数据!");
                pd.put("code", "1");
            } catch (Exception e) {
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



    @RequestMapping(value = "findSuppId",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSuppId(String supplier_id){
        logBefore(logger,"模糊查询供应商");
        PageData pd=this.getPageData();
        if(supplier_id==null||supplier_id.length()==0){
            pd.clear();
            pd.put("code","2");
            pd.put("message","缺少参数!");
        }else{
            try {
                PageData pd_s=supplierService.findById(pd);
                pd.clear();
                pd.put("object", pd_s);
                pd.put("message", "正确返回数据!");
                pd.put("code", "1");
            } catch (Exception e) {
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
     * @param supplier_id 供应商ID
     * @param name 联系人
     * @param gname 供应商名称
     * @param phone 电话
     * @param province 省
     * @param city 市
     * @param county 县
     * @param street 街道
     * @param address 详细
     * @param remarks 备注
     * @param img 头像
     * @param IStatus 判断是否修改了图片 0没有(没修改就把图片路径传过来) 1修改了 2只删除了图片
     * @param identity 身份证
     * @param IStatus1 判断是否修改了图片 0没有(没修改就把图片路径传过来) 1修改了 2只删除了图片
     * @param management_img 经营许可证
     * @return
     */
    @RequestMapping(value = "editSupplier",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String editSupplier(String supplier_id,String name,String phone,String province,String city,
                               String county,String street,String address,String remarks,
                               String gname,String img,String IStatus,String identity,
                               String IStatus1,String management_img){
        logBefore(logger,"修改供应商");
        PageData pd=this.getPageData();
        try{
            if(IStatus!=null){
                if(IStatus.equals("1")){
                    String ffile1 = this.get32UUID() + ".jpg";
                    String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "supplier/" + DateUtil.getDays(); // 文件上传路径
                    File file = new File(filePath2, ffile1);
                    if (!file.exists()) {
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }
                    String ffile = this.get32UUID() + ".jpg";
                    // 文件上传路径
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG +"supplier/"+DateUtil.getDays()+"/"+ ffile;
                    boolean flag = ImageAnd64Binary.generateImage(img, filePath);
                    img = Const.SERVERPATH + Const.FILEPATHIMG+"supplier/"+DateUtil.getDays()+"/"+ ffile;
                }else if(IStatus.equals("0")){
                    img=img;
                }else if(IStatus.equals("2")){
                    img="";
                }
            }else{
                img="";
            }
            if(IStatus1!=null){
                if(IStatus1.equals("1")){
                    if(management_img!=null&&!"".equals(management_img)){
                        String ffile1 = this.get32UUID() + ".jpg";
                        String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "management/" + DateUtil.getDays(); // 文件上传路径
                        File file = new File(filePath2, ffile1);
                        if (!file.exists()) {
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                        }
                        String ffile = this.get32UUID() + ".jpg";
                        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG +"management/"+DateUtil.getDays()+"/"+ ffile; // 文件上传路径
                        boolean flag = ImageAnd64Binary.generateImage(management_img, filePath);
                        management_img = Const.SERVERPATH + Const.FILEPATHIMG+"management/"+DateUtil.getDays()+"/"+ ffile;
                    }else{
                        management_img="";
                    }
                }else if(IStatus1.equals("0")){
                    management_img=management_img;
                }else if(IStatus1.equals("2")){
                    management_img="";
                }
            }else{
                management_img="";
            }
            pd.put("img",img);
            pd.put("management_img",management_img);
            supplierService.edit(pd);
            PageData pd_s=supplierService.findById(pd);
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("data",pd_s);
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
     * @param supplier_id 供应商ID
     * @param store_id 店ID
     * @param uid 用户ID
     * @param money 实收
     * @param owe_money 欠款
     * @param total_money 总价
     * @param remarks 备注
     * @param data  商品数据源（purchase_price:进货价,product_name:商品名字:product_price:商品单价:num:
     *              数量:total:商品总价:norms1:规格1:norms2:规格2:norms3:规格3,product_id:商品ID）
     * @return
     */
    @RequestMapping(value = "saveSupplierOrder",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveSupplierOrderInfo(String supplier_id,String store_id,String uid,String money,
                                        String owe_money,String total_money,String remarks,
                                        String data,String status,String open_bill){
        logBefore(logger,"进货添加订单");
        PageData pd=this.getPageData();
        try{
            PageData pd_ss=supplierService.findById(pd);
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            // 订单号
            String no = s + sdf1.format(now).substring(2);
            pd.put("order_number",no);
            pd.put("date",DateUtil.getTime());
            pd.put("name",pd_ss.getString("name"));
            pd.put("gname",pd_ss.getString("gname"));
            pd.put("phone",pd_ss.getString("phone"));
            if(open_bill==null){
                pd.put("open_bill","");
            }
            supplierOrderInfoService.save(pd);
            data = DateUtil.delHTMLTag(data);
            data = data.replace("\r", "");
            data = data.replace("\n", "");
            data = data.replace("\\", "");
            data = data.replace(" ", "XINg");
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(data, new TypeToken<List<PageData>>() {
            }.getType());   //获取订单商品列表
            supplierOrderProService.batchSave(list,pd.get("supplier_order_info_id").toString());
            PageData pd_s=supplierService.findById(pd);
            Double owe=0.0;
            if(Double.valueOf(owe_money)>0){
                owe=Double.valueOf(pd_s.get("owe").toString())+Double.valueOf(owe_money);
                pd_s.put("owe",owe.toString());
                supplierService.editOwe(pd_s);
            }else{
                owe=Double.valueOf(pd_s.get("owe").toString());
            }
            for(int i=0;i<list.size();i++){
                if(list.get(i).get("product_id")!=null){
                    double product_id=Double.valueOf(list.get(i).get("product_id").toString());
                    int product_ids=(int)product_id;
                    list.get(i).put("product_id",product_ids);
                    productService.editNumJia(list.get(i));
                }
            }
            kunCunService.batchSaves(list,store_id,DateUtil.getTime(),"2",supplier_id,pd.get("supplier_order_info_id").toString(),pd.get("supplier_order_info_id").toString());
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("owe",owe.toString());
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
     * @param supplier_id 供应商ID
     * @param status 0停用  1启用
     * @return
     */
    @RequestMapping(value = "deleteSupplier",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteSupplier(String supplier_id,String status){
        logBefore(logger,"删除供应商");
        PageData pd=this.getPageData();
        try{
            if("0".equals(status)){
                pd.put("status","1");
            }else{
                pd.put("status","0");
            }
            supplierService.editStatus(pd);
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


    @RequestMapping(value = "findSupplierOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSupplierOrderInfo(String store_id,String pageNum,String supplier_id,
                                        String startTime,String endTime){
        logBefore(logger,"查询进货订单");
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
            List<PageData> list=supplierOrderInfoService.datalistPage(page);
            for(int i=0;i<list.size();i++){
                List<PageData> list_pro=supplierOrderProService.findList(list.get(i));
                list.get(i).put("order_pro",list_pro);
            }
            PageData pd_c=supplierOrderInfoService.findCounts(pd);
            String totalMoney="0.0";
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
            PageData pdMoney=supplierOrderInfoService.findDateSumMoney(pd);
            if(pdMoney!=null&&!"0.0".equals(pdMoney.get("money").toString())){
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
            pd.put("count",pd_c.get("count").toString());
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


    @RequestMapping(value = "findlikeSupplierOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findlikeSupplierOrderInfo(String store_id,String name){
        logBefore(logger,"查询退货订单");
        PageData pd=this.getPageData();
        try{
            List<PageData> list=supplierOrderInfoService.findLikeOrderInfo(pd);
            for(int i=0;i<list.size();i++){
                List<PageData> list_pro=supplierOrderProService.findList(list.get(i));
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

    @RequestMapping(value = "findlikeSupplierOrderInfoId",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findlikeSupplierOrderInfoId(String supplier_order_info_id){
        logBefore(logger,"查询详情");
        PageData pd=this.getPageData();
        try{
            PageData pd_o=supplierOrderInfoService.findById(pd);
            List<PageData> list=supplierOrderProService.findList(pd_o);
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


    /**
     *
     * @param supplier_id 供应商id
     * @param product_id  商品ID
     * @param store_id 店ID
     * @param status 0关联  1取消关联
     * @return
     */
    @RequestMapping(value = "RelationProduct",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String RelationProduct(String supplier_id,String product_id,String store_id,String status){
        logBefore(logger,"关联商品");
        PageData pd=this.getPageData();
        try{
            if(status.equals("0")){
                productService.editSupplierId(pd);
            }else{
                pd.put("supplier_id","0");
                productService.editSupplierId(pd);
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

    @RequestMapping(value = "deleteRelation",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteRelation(String relation_id){
        logBefore(logger,"取消关联");
        PageData pd=this.getPageData();
        try{
            pd.put("supplier_id","0");
            productService.editSupplierId(pd);
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

    @RequestMapping(value = "findRelation",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findRelation(String supplier_id,String store_id){
        logBefore(logger,"查询关联的商品");
        PageData pd=this.getPageData();
        java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
        try{
            List<PageData> list=productService.findSupplierList(pd);
            for(int i=0;i<list.size();i++){
                PageData pd_sp=orderKuncunService.findSumProduct(list.get(i));
                PageData pd_s=supplierOrderProService.findSumPro(list.get(i));
                if(pd_sp!=null&&pd_sp.get("money")!=null&&!pd_sp.get("money").toString().equals("0.0")){
                    list.get(i).put("sale_money",df.format(pd_sp.get("money")));
                }else{
                    list.get(i).put("sale_money","0.0");
                }
               if(pd_s!=null&&!pd_s.get("money").toString().equals("0.0")){
                   list.get(i).put("money",df.format(pd_s.get("money")));
                }else{
                   list.get(i).put("money","0.0");
               }
            }
            Map<String, Object> map = new HashedMap();
            map.put("object",list);
            pd.clear();
            pd.put("object",map);
            pd.put("code","1");
            pd.put("message","正确返回数据");
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

    @RequestMapping(value = "findSupplierAnalysis",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSupplierAnalysis(String supplier_id){
        logBefore(logger,"查询供应商的商品利润");
        PageData pd=this.getPageData();
        java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
        try{
            List<PageData> list=productService.findSupplierFeiXi(pd);
            double profit=0.0;
            double receivable=0.0;
            if(list!=null&&list.size()!=0){
                profit=list.stream().mapToDouble(PageData::getProfit).sum();
                receivable=list.stream().mapToDouble(PageData::getReceivable).sum();
            }
            pd.clear();
            pd.put("data",list);
            if(profit!=0.0){
                pd.put("total_profit",df.format(profit));
            }else{
                pd.put("total_profit",profit);
            }
            if(receivable!=0.0){
                pd.put("total_receivable",df.format(receivable));
            }else{
                pd.put("total_receivable",receivable);
            }
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

    @RequestMapping(value = "findSupplierDetails",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSupplierDetails(String supplier_id){
        logBefore(logger,"查询供应商详情");
        PageData pd=this.getPageData();
        try{
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
            PageData pd_s=supplierService.findById(pd);
            PageData pd_c=orderKuncunService.findSupplierProduct(pd);
            PageData pd_sup=supplierOrderInfoService.findSumMoney(pd);
            String money="0.0";
            String purchase_money="0.0";
            pd_s.put("money","0.0");
            pd_s.put("sale_money","0.0");
            if(pd_c!=null){
                if(!pd_c.get("money").toString().equals("0.0")){
                    money=df.format(pd_c.get("money"));
                    pd_s.put("sale_money",money);
                }
            }
            if(pd_sup!=null){
                if(!pd_sup.get("money").toString().equals("0.0")){
                    purchase_money=df.format(pd_sup.get("money"));
                    pd_s.put("money",purchase_money);
                }
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("money",money);
            pd.put("purchase_money",purchase_money);
            pd.put("supplier",pd_s);
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

    @RequestMapping(value = "revokesOrder",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String revokesOrder(String supplier_order_info_id){
        logBefore(logger,"撤销进货订单");
        PageData pd=this.getPageData();
        ObjectMapper mapper=new ObjectMapper();
        try{
            PageData pd_o=supplierOrderInfoService.findById(pd);
            if(!pd_o.getString("date").split(" ")[0].equals(DateUtil.getDay())){
                pd.clear();
                pd.put("code","2");
                pd.put("message","订单不是今天订单不可撤销!");
                return mapper.writeValueAsString(pd);
            }
            PageData pd_k=kunCunService.findSumNum(pd);
            if(!pd_k.get("num").toString().equals(pd_k.get("nums").toString())){
                pd.clear();
                pd.put("code","2");
                pd.put("message","已经处理过的订单不可撤销!");
                return mapper.writeValueAsString(pd);
            }
            List<PageData> supplierList=kunCunService.findReturnSupplierList(pd);
            if(supplierList!=null&&supplierList.size()!=0){
                pd.clear();
                pd.put("code","2");
                pd.put("message","该订单有退货,不可撤销!");
                return mapper.writeValueAsString(pd);
            }
            pd.put("supplier_order_info_id",supplier_order_info_id);
            supplierOrderInfoService.editRevokes(pd);
            kunCunService.editRevokes(pd);
            List<PageData> listSu=supplierOrderProService.findList(pd);
            for(int i=0;i<listSu.size();i++){
                productService.editNum(listSu.get(i));
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
     * @param supplier_order_info_id 进货订单ID
     * @param data 商品数据源(product_name:商品名字,
     *             product_price:销售价,num:数量,total:小计,
     *             norms1:规格1,norms2:规格2,norms3:规格3,product_id:商品ID)
     * @param money 实收
     * @param store_id 店ID
     * @param remarks 备注
     * @param open_bill 开单人
     * @return
     */
    @RequestMapping(value = "returnSupplierOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String returnSupplierOrderInfo(String supplier_order_info_id,String data,
                                          String money,String store_id,String remarks,
                                          String open_bill){
        logBefore(logger,"进货订单退货");
        PageData pd=this.getPageData();
        ObjectMapper mapper=new ObjectMapper();
        try{
            PageData pd_s=supplierOrderInfoService.findById(pd);
            pd_s.put("return_date",DateUtil.getTime());
            pd_s.put("money",money);
            pd_s.put("total_money",money);
            pd_s.put("remarks",remarks);
            pd_s.put("open_bill",open_bill);
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            // 订单号
            String no = s + sdf1.format(now).substring(2);
            pd_s.put("order_number",no);
            pd_s.put("status","0");
            returnSupplierOrderInfoService.save(pd_s);
            data = DateUtil.delHTMLTag(data);
            data = data.replace("\r", "");
            data = data.replace("\n", "");
            data = data.replace("\\", "");
            data = data.replace(" ", "XINg");
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(data, new TypeToken<List<PageData>>() {
            }.getType());   //获取订单商品列表
            for(int i=0;i<list.size();i++){
                list.get(i).put("store_id",store_id);
                list.get(i).put("product_id",list.get(i).get("product_id").toString());
                List<PageData> lists=kunCunService.findList(list.get(i));
                String kucun=list.get(i).get("num").toString();
                if(lists!=null&&lists.size()!=0){
                    for (int j=0;j<lists.size();j++){
                        int in=(new Double(kucun)).intValue();
                        if(Integer.valueOf(lists.get(j).get("nums").toString())>=in){
                            kucun="0";
                            lists.get(j).put("nums",Integer.valueOf(lists.get(j).get("nums").toString())-Integer.valueOf(in));
                            kunCunService.editNum(lists.get(j));
                        }else{
                            if(j==lists.size()-1){
                                kucun="0";
                                lists.get(j).put("nums","0");
                                kunCunService.editNum(lists.get(j));
                            }else{
                                Integer kuncuns=Integer.valueOf(in)-Integer.valueOf(lists.get(j).get("nums").toString());
                                kucun=kuncuns.toString();
                                lists.get(j).put("nums","0");
                                kunCunService.editNum(lists.get(j));
                            }
                        }
                    }
                }
                productService.editNum(list.get(i));
                list.get(i).put("return_supplier_order_info_id",pd_s.get("return_supplier_order_info_id").toString());
                returnSupplierOrderProService.save(list.get(i));
            }
            kunCunService.batchSavess(list,store_id,DateUtil.getTime(),"4",pd_s.get("supplier_id").toString(),pd_s.get("supplier_order_info_id").toString(),"1",pd_s.get("return_supplier_order_info_id").toString());
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
        String str="";
        try {
            str= mapper.writeValueAsString(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "findReturnSupplierOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findReturnSupplierOrderInfo(String store_id,String startTime,
                                              String endTime,String name,String pageNum){
        logBefore(logger,"查询退货订单");
        PageData pd=this.getPageData();
        Page page=new Page();
        java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
        try {
            String message="正确返回数据!";
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(100);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list=supplierOrderInfoService.datalistPage(page);
            for(int i=0;i<list.size();i++){
                List<PageData> listPro=supplierOrderProService.findList(list.get(i));
                list.get(i).put("pro",listPro);
            }
            String totalMoeny="0.0";
            PageData pdTotal=supplierOrderInfoService.findTotalSumMoney(pd);
            if(pdTotal!=null&&!"0.0".equals(pdTotal.get("money").toString())){
                totalMoeny=df.format(pdTotal.get("money"));
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("data", list);
            }else{
                map.put("message",message);
                map.put("data",new ArrayList());
            }
            pd.clear();
            pd.put("object", map);
            pd.put("message", message);
            pd.put("code", "1");
            pd.put("pageTotal",String.valueOf(page.getTotalPage()));
            pd.put("totalMoeny",totalMoeny);
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

    @RequestMapping(value = "RevokesRetrunSupplierOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String RevokesRetrunSupplierOrderInfo(String return_supplier_order_info_id){
        logBefore(logger,"撤销进货退货账单");
        PageData pd=this.getPageData();
        try {
            pd.put("revokes","1");
            PageData pdReturn=returnSupplierOrderInfoService.findById(pd);
            returnSupplierOrderInfoService.editRevokes(pd);
            List<PageData> list=returnSupplierOrderProService.findList(pd);
            for(int i=0;i<list.size();i++){
                list.get(i).put("supplier_order_info_id",pdReturn.get("supplier_order_info_id").toString());
                list.get(i).put("id",return_supplier_order_info_id);
                PageData pdKuCun=kunCunService.findReturnSupplierProduct(list.get(i));
                pdKuCun.put("nums",Integer.valueOf(pdKuCun.get("nums").toString())+Integer.valueOf(list.get(i).get("num").toString()));
                kunCunService.editJiaNums(pdKuCun);
                productService.editJiaNums(list.get(i));
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

    @RequestMapping(value = "findRetrunSupplierOrderInfoId",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findRetrunSupplierOrderInfoId(String return_supplier_order_info_id){
        logBefore(logger,"查询进货退货详情");
        PageData pd=this.getPageData();
        try{
            PageData pd_o=returnSupplierOrderInfoService.findById(pd);
            List<PageData> list=returnSupplierOrderProService.findList(pd);
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

    @RequestMapping(value = "findRetrunSupplierOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findRetrunSupplierOrderInfo(String store_id,String pageNum,
                                              String startTime,String endTime,String name){
        logBefore(logger,"查询进货退货账单");
        PageData pd=this.getPageData();
        Page page=new Page();
        try{
            String message="正确返回数据!";
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(100);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list=returnSupplierOrderInfoService.datalistPage(page);
            for(int i=0;i<list.size();i++){
                List<PageData> listPro=returnSupplierOrderProService.findList(list.get(i));
                list.get(i).put("pro",listPro);
            }
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.0");
            String total_money="0.0";
            PageData pdMoney=returnSupplierOrderInfoService.findSumMoney(pd);
            if(pdMoney!=null&&pdMoney.get("money").toString()!=null){
                total_money=df.format(pdMoney.get("money"));
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("data", list);
            }else{
                map.put("message",message);
                map.put("data",new ArrayList());
            }
            pd.clear();
            pd.put("object", map);
            pd.put("message", message);
            pd.put("code", "1");
            pd.put("pageTotal",String.valueOf(page.getTotalPage()));
            pd.put("count",String.valueOf(page.getShowCount()));
            pd.put("total_money",total_money);
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


    @RequestMapping(value = "findOweSupplier",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findOweSupplier(String store_id,String pageNum,String name){
        logBefore(logger,"查询欠款供应商");
        PageData pd=this.getPageData();
        Page page=new Page();
        try{
            String message="正确返回数据!";
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(100);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list=supplierService.owelistPage(page);
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.0000");
            for(int i=0;i<list.size();i++){
                PageData pd_money=supplierOrderInfoService.findSumMoney(list.get(i));
                if(pd_money!=null&&!pd_money.get("money").toString().equals("0.0")){
                    list.get(i).put("money",df.format(pd_money.get("money")));
                    //total_money+=Double.valueOf(total_money)+Double.valueOf(pd_money.get("money").toString());
                }else{
                    list.get(i).put("money","0.0");
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("data", list);
            }else{
                map.put("message",message);
                map.put("data",new ArrayList());
            }
            pd.clear();
            pd.put("object", map);
            pd.put("message", message);
            pd.put("code", "1");
            pd.put("pageTotal",String.valueOf(page.getTotalPage()));
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


    @RequestMapping(value = "findOweOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findOweOrderInfo(String store_id,String supplier_id,String pageNum){
        logBefore(logger,"查询欠款的进货订单");
        PageData pd=this.getPageData();
        Page page=new Page();
        try{
            String message="正确返回数据!";
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(100);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list=supplierOrderInfoService.owelistPage(page);
            for(int i=0;i<list.size();i++){
                List<PageData> listPro=supplierOrderProService.findList(list.get(i));
                list.get(i).put("pro",listPro);
            }
            String owe_money="0";
            String already_money="0";
            PageData pdMoney=supplierOrderInfoService.findSumOwe(pd);
            PageData pdRepayments=repaymentsService.findSum(pd);
            if(pdMoney!=null){
                owe_money=pdMoney.get("money").toString();
            }
            if(pdRepayments!=null){
                already_money=pdRepayments.get("money").toString();
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("data", list);
            }else{
                map.put("message",message);
                map.put("data",new ArrayList());
            }
            pd.clear();
            pd.put("object", map);
            pd.put("message", message);
            pd.put("code", "1");
            pd.put("pageTotal",String.valueOf(page.getTotalPage()));
            pd.put("owe_money",owe_money);
            pd.put("already_money",already_money);
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
     * @param uid ID
     * @param pageNum 页码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param name 模糊查询
     * @param store_id 不传查询全部  传就查询某个店的
     * @return
     */
    @RequestMapping(value = "findAdminRelationOrderInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAdminRelationOrderInfo(String uid,String pageNum,String startTime,
                                             String endTime,String name,String store_id){
        logBefore(logger,"查询所有订单");
        PageData pd=this.getPageData();
        Page page=new Page();
        try{
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list=supplierOrderInfoService.AdminlistPage(page);
            for (int i = 0, len = list.size(); i < len; i++) {
                List<PageData> list_pro=supplierOrderProService.findList(list.get(i));
                list.get(i).put("pro",list_pro);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("data",list);
            }else{
                map.put("message","正确返回数据!");
                map.put("data",new ArrayList());
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            pd.put("pageTotal",String.valueOf(page.getTotalPage()));
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



    @RequestMapping(value = "findExcelUtil",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void findExcelUtil(String store_id, HttpServletRequest request, HttpServletResponse response){
        try{
            PageData pd=this.getPageData();
            List<PageData> list=supplierOrderProService.findQuanBu(pd);
            SupplierOrderExtend.printExcle(list,"E://ceshi.xls",request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "findReturnExcelUtil",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void findReturnExcelUtil(String store_id, HttpServletRequest request, HttpServletResponse response){
        try{
            PageData pd=this.getPageData();
            List<PageData> list=returnSupplierOrderProService.findQuanBu(pd);
            ReturnSupplierOrderExtend.printExcle(list,"E://ceshi.xls",request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "findAdminExcelUtil",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void findAdminExcelUtil(String store_id, HttpServletRequest request, HttpServletResponse response){
        try{
            PageData pd=this.getPageData();
            List<PageData> list=supplierOrderProService.findAdminQuanBu(pd);
            AdminSupplieExtend.printExcle(list,"E://ceshi.xls",request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}