package com.mtnz.controller.app.repayments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.controller.base.BaseController;
import com.mtnz.entity.Page;
import com.mtnz.service.system.repayments.RepaymentsService;
import com.mtnz.service.system.supplier.SupplierService;
import com.mtnz.util.DateUtil;
import com.mtnz.util.PageData;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\6\29 0029.  
 */
@Controller
@RequestMapping(value = "app/repayments")
public class AppRepaymentsController extends BaseController {
    @Resource(name = "repaymentsService")
    private RepaymentsService repaymentsService;
    @Resource(name = "supplierService")
    private SupplierService supplierService;


    /**
     * @param money       钱数
     * @param supplier_id 供应商ID
     * @param store_id    店ID
     * @param discount    优惠
     * @return
     */
    @RequestMapping(value = "saveRepayments", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveRepayments(String supplier_id, String money, String store_id, String discount) {
        logBefore(logger, "还钱给供应商");
        PageData pd = this.getPageData();
        try {
            String date = DateUtil.getTime();
            pd.put("date", date);
            pd.put("out_trade_no", "");
            pd.put("status", "1");
            if (discount == null || discount.length() == 0) {
                pd.put("discount", "0");
            }
            repaymentsService.save(pd);
            PageData pdSupplier = supplierService.findById(pd);
            if (discount != null || discount.length() != 0) {
                pdSupplier.put("owe", Double.valueOf(pdSupplier.getString("owe")) - Double.valueOf(money) - Double.valueOf(discount));
            } else {
                pdSupplier.put("owe", Double.valueOf(pdSupplier.getString("owe")) - Double.valueOf(money));
            }
            supplierService.editOwe(pdSupplier);
            String total_money="0";
            PageData pd_sum=repaymentsService.findSum(pd);
            if(pd_sum!=null){
                total_money=pd_sum.get("money").toString();
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("owe", pdSupplier.get("owe").toString());
            pd.put("date", date);
            pd.put("total_money",total_money);
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

    @RequestMapping(value = "findRepayments", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findRepayments(String supplier_id, String pageNum) {
        logBefore(logger, "查询已经还过款的记录");
        PageData pd = this.getPageData();
        Page page = new Page();
            String message = "正确返回数据!";
            try {
                if (pageNum == null || pageNum.length() == 0) {
                    pageNum = "1";
                }
                page.setPd(pd);
                page.setShowCount(10);
                page.setCurrentPage(Integer.parseInt(pageNum));
                List<PageData> list = repaymentsService.datalistPage(page);
                Map<String, Object> map = new HashedMap();
                if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                    map.put("data", list);
                } else {
                    map.put("message", message);
                    map.put("data", new ArrayList());
                }
                pd.clear();
                pd.put("object", map);
                pd.put("code", "1");
                pd.put("message", message);
                pd.put("pageTotal", String.valueOf(page.getTotalPage()));
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