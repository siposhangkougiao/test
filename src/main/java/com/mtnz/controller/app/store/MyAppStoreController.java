package com.mtnz.controller.app.store;

/**
 * 新建 2020-04-07
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;

import com.mtnz.service.system.customer.CustomerService;
import com.mtnz.service.system.product.ProductService;
import com.mtnz.service.system.store.MyStoreService;
import com.mtnz.service.system.store.StoreService;
import com.mtnz.service.system.sys_app_user.SysAppUserService;
import com.mtnz.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 新店铺注册
 */
@Controller
@RequestMapping(value = "app/mystore",produces = "text/html;charset=UTF-8")
public class MyAppStoreController extends BaseController{

    @Resource(name = "myStoreService")
    private MyStoreService myStoreService;
    @Resource(name = "storeService")
    private StoreService storeService;
    @Resource(name = "customerService")
    public CustomerService customerService;
    @Resource(name = "sysAppUserService")
    private SysAppUserService sysAppUserService;
    @Resource(name = "productService")
    private ProductService productService;

    /**
     * 获取注册验证码
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
            String context = "验证码是【" + yzm + "】有效时间:5分钟";
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
     * 注册添加
     * @param username
     * @param phone
     * @return
     */
    @RequestMapping(value = "saveStore",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveStore(String username,String phone,String code){
        logBefore(logger,"注册添加");
        PageData pd=this.getPageData();
        try{
            PageData pd2 = new PageData();
            pd2.put("code", code);
            pd2.put("phone", phone);
            pd2.put("now_time", new Date());
            PageData codepage = myStoreService.findyzmBycode(pd2);
            if(codepage==null){
                return getMessage("-101","验证码失效，请重新发送");
            }else {
                myStoreService.editCode(pd2);
            }
            PageData pd_u=sysAppUserService.findUserName(pd);
            if(pd_u!=null){
                pd.clear();
                pd.put("code","2");
                pd.put("message","用户名已存在");
            }else{
                PageData pd_s=new PageData();
                pd_s.put("name",phone + "的店");
                pd_s.put("number","200");
                pd_s.put("address","");
                pd_s.put("qr_code","");
                pd_s.put("phone","");
                pd_s.put("province","");
                pd_s.put("city","");
                pd_s.put("county","");
                pd_s.put("street","");
                pd_s.put("business_img","");
                pd_s.put("phoneTow",phone);
                pd_s.put("is_pass",0);
                storeService.save(pd_s);    //先添加店
                pd.put("username",username);
                pd.put("name",username);
                pd.put("salt", CharacterUtils.getRandomString(4));
                String password = GetStrings.getRandomNickname(8);
                pd.put("password", MD5.md5(MD5.md5(password) + pd.getString("salt")));
                pd.put("openid","");
                pd.put("unionid","");
                pd.put("status","0");
                pd.put("store_id",pd_s.get("store_id"));
                pd.put("date", DateUtil.getTime());
                pd.put("login_date", "");
                pd.put("identity", 1);
                pd.put("diedstatus", 0);
                sysAppUserService.save(pd);
                SmsBao smsBao=new SmsBao();
                //smsBao.sendSMS(phone,"你的账号已创建成功,账号为"+username+"初始密码为123456，请赶快修改密码");
                smsBao.sendSMS(phone,"您的账号已创建，账号:"+username+"初始密码:"+password);
                PageData pd_customer=new PageData();
                pd_customer.put("name","零散客户");
                pd_customer.put("phone","");
                pd_customer.put("address","");
                pd_customer.put("crop","");
                pd_customer.put("area","");
                pd_customer.put("input_date", DateUtil.getTime());
                pd_customer.put("billing_date","");
                pd_customer.put("owe","0");
                pd_customer.put("status","3");
                pd_customer.put("store_id",pd_s.get("store_id"));
                pd_customer.put("uid",pd.get("uid"));
                pd_customer.put("province","");
                pd_customer.put("city","");
                pd_customer.put("county","");
                pd_customer.put("street","");
                pd_customer.put("img","");
                pd_customer.put("identity","");
                pd_customer.put("remarks","");
                customerService.save(pd_customer);
                PageData pageData = new PageData();
                pageData.put("user_id",pd.get("uid"));
                pageData.put("store_id",pd_s.get("store_id"));
                pageData.put("status",0);
                pageData.put("ismr",1);
                storeService.saveStoreUser(pageData);
                pd.clear();
                pd.put("code","1");
                pd.put("message","正确返回数据!");
            }
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
     * 修改店铺信息
     * @param store_id 店铺id
     * @param door_first  门头照
     * @param license_img  营业执照
     * @param province  省
     * @param city 市
     * @param district  区
     * @param address 详细地址
     * @param name  店铺名称
     * @param is_pass  是否通过审核  选填 1通过  2拒绝
     * @return
     */
    @RequestMapping(value = "updateStore",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateStore(Long store_id,String door_first,String license_img,String province,
                              String city,String district,String address,String name,Integer is_pass){
        logBefore(logger,"修改店铺信息");
        PageData pd=this.getPageData();
        try{
            myStoreService.editStore(pd);
            if(is_pass!=null&&is_pass==1){
                PageData pageData = storeService.findById(pd);
                if(pageData.get("phoneTow")!=null){
                    SmsBao smsBao = new SmsBao();
                    smsBao.sendSMS(pageData.get("phoneTow").toString(),"您的店铺已认证通过！请使用手机号尾号："+pageData.get("phoneTow").toString().substring(7)+"登录，即可享用VIP权益");
                }
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
     * 查询审核列表
     * @Param is_pass 0未审核  1审核通过  2审核失败
     * @return
     */
    @RequestMapping(value = "findRegisterList",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findRegisterList(Integer is_pass){
        logBefore(logger,"查询审核列表");
        PageData pd=this.getPageData();
        try{

            List<PageData> list = myStoreService.findRegisterList(pd);
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
     * 查询店铺审核结果
     * @param store_id
     * @return
     */
    @RequestMapping(value = "selectResult",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String selectResult(Long store_id){
        logBefore(logger,"查询店铺审核结果");
        PageData pd=this.getPageData();
        try{
            PageData list = myStoreService.selectResult(pd);
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
     * 图片上传
     * @param img_code
     * @return
     */
    @RequestMapping(value = "uploadimg", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveQrCode(String img_code) {
        logBefore(logger, "添加店铺图片");
        PageData pd = this.getPageData();
        try {
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            String ffile = this.get32UUID() + ".jpg";
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + DateUtil.getDays() + "/" + ffile; // 文件上传路径
            boolean flag = ImageAnd64Binary.generateImage(img_code, filePath);
            img_code = Const.SERVERPATH + Const.FILEPATHIMG + DateUtil.getDays() + "/" + ffile;
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data",img_code);
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
     * 刷数据库
     * @return
     */
    @RequestMapping(value = "testimage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String testimage() {
        logBefore(logger, "添加店铺图片");
        PageData pd = this.getPageData();
        try {
            List<PageData> list = productService.findproductall();
            Integer cc =0;
            List<PageData> xx = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).get("product_img")!=null){
                    String aa = list.get(i).get("product_img").toString();
                    aa = aa.replace("115.28.210.33:9090","new.nongshoping.com:8080");
                    PageData pageData = new PageData();
                    pageData.put("product_id",list.get(i).get("product_id"));
                    pageData.put("product_img",aa);
                    xx.add(pageData);
                    cc+=1;
                    if(cc==500){
                        productService.editProductallxx(xx);
                        cc=0;
                        xx.clear();
                    }
                    //productService.editProductall(pageData);
                }
            }
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

}
