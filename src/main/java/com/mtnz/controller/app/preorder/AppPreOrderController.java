package com.mtnz.controller.app.preorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value = "app/preorder")
public class AppPreOrderController extends BaseController{

    /**
     * 查询销售单详情
     * @param order_info_id 订单ID
     * @return
     */
    @RequestMapping(value = "findOrderInfoById",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findOrderInfoById(String order_info_id){
        logBefore(logger,"查询订单详情");
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

}
