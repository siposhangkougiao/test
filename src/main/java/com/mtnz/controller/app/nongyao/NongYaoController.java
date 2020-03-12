package com.mtnz.controller.app.nongyao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.service.system.nongyao.NongYaoService;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\6\9 0009.  
 */
@Controller
@RequestMapping(value = "app/nongyao",produces = "text/html;charset=UTF-8")
public class NongYaoController extends BaseController{
    @Resource(name = "nongYaoService")
    private NongYaoService nongYaoService;


    @RequestMapping(value = "findList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void findList(){
        try{
            PageData pd=new PageData();
            List<PageData> list=nongYaoService.findListsss(pd);
            for(int i=0;i<list.size();i++){
               list.get(i).put("NONGYAO_ID",this.get32UUID());
               nongYaoService.edit(list.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param type (PD,LS,WP)
     * @param number 数字
     * @return
     */
    @RequestMapping(value = "findlikeNongYao", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findlikeNongYao(String type,String number){
        logBefore(logger,"查询农药");
        PageData pd=this.getPageData();
        try{
            PageData pd_s=nongYaoService.findlikeNongYao(pd);
            if(pd_s==null){
                pd_s=new PageData();
                pd_s.put("REGISTER_NUMBER","");
                pd_s.put("NAME","");
                pd_s.put("QUANBU","");
                pd_s.put("ENAME","");
                pd_s.put("TYPE","");
            }
            pd.clear();
            pd.put("code","1");
            pd.put("message","正确返回数据!");
            if(number!=null||number.length()!=0){
                pd.put("data",pd_s);
            }
        }catch (Exception e){
            logBefore(logger,"查询农药出错"+e);
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
