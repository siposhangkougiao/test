package com.mtnz.controller.system.product;

import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.product.WeProductService;
import com.mtnz.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\4\2 0002.  
 */
@Controller
@RequestMapping(value = "weproduct",produces = "text/html;charset=UTF-8")
public class WeProductController extends BaseController{
    String menuUrl="weproduct/list.do";
    @Resource(name = "weProductService")
    private WeProductService weProductService;


    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除商品");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            weProductService.delete(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }

    }

    // 删除首页图片
    @RequestMapping(value = "/deltp")
    @ResponseBody
    public String deltp(PrintWriter out) {
        logBefore(logger, "删除图片");
        try {
            PageData pd = this.getPageData();

            String PATH = pd.getString("product_img"); // 图片路径
            if(PATH!=null&&PATH!=""){
                String path[] = PATH.split("uploadFiles");
                if(path.length > 1){
                    String DPath = "uploadFiles"+path[1];
                    DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
                }
                pd=weProductService.findById(pd);
                pd.put("product_img", "");
                weProductService.updateProduct(pd);
            }
            out.write("success");
            out.close();
            return "success";
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return "fail";
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value="/edit")
    public ModelAndView tedit() throws Exception{
        logBefore(logger, "修改商品");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        weProductService.updateProduct(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        logBefore(logger, "去修改商品页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        try {
            pd = weProductService.findById(pd);	//根据ID读取
            mv.setViewName("system/weproduct/product_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd(){
        logBefore(logger, "去新增商品页面");
        ModelAndView mv = this.getModelAndView();
        try {
            mv.setViewName("system/weproduct/product_edit");
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 新增
     */
    @RequestMapping(value="/save")
    public ModelAndView save() throws Exception{
        logBefore(logger, "新增商品");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd.put("date", DateUtil.getTime());
        pd.put("status","0");
        weProductService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "查询商品列表");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        try{
            //页面检索
            String KEYWORD = pd.getString("KEYWORD");
            if (null != KEYWORD && !"".equals(KEYWORD)) {
                KEYWORD = KEYWORD.trim();
                pd.put("KEYWORD", KEYWORD);
            }
            page.setPd(pd);
            List<PageData>	varList = weProductService.list(page);	//列出Pro_Info列表
            mv.setViewName("system/weproduct/product_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }

    /* ===============================权限================================== */
    public Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */
}
