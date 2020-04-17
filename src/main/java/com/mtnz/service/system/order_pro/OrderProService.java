package com.mtnz.service.system.order_pro;


import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\3\21 0021.
 */

@Service("orderProService")
public class OrderProService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("OrderProMapper.save",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findById",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findList",pd);
    }

    public void batchSave(List<PageData> list,String order_info_id) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        map.put("order_info_id",order_info_id);
        daoSupport.save("OrderProMapper.batchSave",map);
    }

    public PageData findProductProCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findProductProCount",pd);
    }

    public List<PageData> findProductPro(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findProductPro",pd);
    }

    public PageData findProNum(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findProNum",pd);
    }

    public PageData findProMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findProMoney",pd);
    }

    public PageData findSumProduct(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findSumProduct",pd);
    }

    public PageData findSumProducts(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findSumProducts",pd);
    }

    public void editStatus(PageData pd) throws Exception {
        daoSupport.update("OrderProMapper.editStatus",pd);
    }

    public void editOrderKuncun(PageData pd) throws Exception {
        daoSupport.update("OrderProMapper.editOrderKuncun",pd);
    }

    public void editOrderKuncuns(PageData pd) throws Exception {
        daoSupport.update("OrderProMapper.editOrderKuncuns",pd);
    }


    public List<PageData> findLists(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findLists",pd);
    }

    public PageData findSum(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findSum",pd);
    }

    public List<PageData> findProductList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findProductList",pd);
    }

    public void editRevokes(PageData pd) throws Exception {
        daoSupport.update("OrderProMapper.editRevokes",pd);
    }

    public PageData findCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findCount",pd);
    }

    public PageData findOrderInfoProduct(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("OrderProMapper.findOrderInfoProduct",pd);
    }

    public List<PageData> findQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findQuanBu",pd);
    }

    public List<PageData> findAdminQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findAdminQuanBu",pd);
    }

    public void editOrderKuncunli(PageData pd) throws Exception {
        daoSupport.update("OrderProMapper.editOrderKuncunli",pd);
    }

    public void editOrderKuncunsli(PageData pd) throws Exception {
        daoSupport.update("OrderProMapper.editOrderKuncunsli",pd);
    }

    public List<PageData> findOrderProNowNumber(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findOrderProNowNumber",pd);
    }

    public List<PageData> findOrderProList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findOrderProList",pd);
    }

    public List<PageData> findorderByOpenBillDetail(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderProMapper.findorderByOpenBillDetail",pd);
    }
}
