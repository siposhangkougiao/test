package com.mtnz.service.system.customer;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\3\21 0021.
 */

@Service("customerService")
public class CustomerService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("CustomerMapper.save",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("CustomerMapper.findById",pd);
    }

    public List<PageData> dataListPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.datalistPage",page);
    }

    public void  updateCustomer(PageData pd) throws Exception {
        daoSupport.update("CustomerMapper.updateCustomer",pd);
    }

    public void batchSaveCustomer(List<PageData> list,String store_id,String input_date,String uid) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        map.put("store_id",store_id);
        map.put("input_date",input_date);
        map.put("uid",uid);
        daoSupport.save("CustomerMapper.batchSaveCustomer",map);
    }

    public PageData findNameCustomer(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("CustomerMapper.findNameCustomer",pd);
    }

    public void updateBillingDate(PageData pd) throws Exception {
        daoSupport.update("CustomerMapper.updateBillingDate",pd);
    }

    public void updateOwe(PageData pd) throws Exception {
        daoSupport.update("CustomerMapper.updateOwe",pd);
    }

    public List<PageData> findLikename(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findLikename",pd);
    }

    public List<PageData> owelistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.owelistPage",page);
    }

    public List<PageData> findRepeatCustomer(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findRepeatCustomer",pd);
    }

    public List<PageData> findRepeatCustomerPhone(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findRepeatCustomerPhone",pd);
    }

    public void deleteAll(List<PageData> list) throws Exception {
        daoSupport.delete("CustomerMapper.delete",list);
    }

    public void batchPpdate(List<PageData> list) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        daoSupport.update("CustomerMapper.batchUpdate",map);
    }

    public void updateStatus(PageData pd) throws Exception {
        daoSupport.update("CustomerMapper.updateStatus",pd);
    }

    public List<PageData> findLikeowelist(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findLikeowelist",pd);
    }

    public PageData findCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("CustomerMapper.findCount",pd);
    }

    public PageData findSumOwe(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("CustomerMapper.findSumOwe",pd);
    }

    public List<PageData> findCustomerList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findCustomerList",pd);
    }

    public List<PageData> dataslistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.dataslistPage",page);
    }

    public List<String> findListss(PageData pd) throws Exception {
        return (List<String>) daoSupport.findForList("CustomerMapper.findListss",pd);
    }

    public List<PageData> findpurchaselist(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findpurchaselist",pd);
    }

    public PageData Purchaselistcount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("CustomerMapper.Purchaselistcount",pd);
    }

    public List<PageData> findCustomerName(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findCustomerName",pd);
    }

    public List<PageData> findCustomerPhone(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findCustomerPhone",pd);
    }

    public PageData findLingSan(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("CustomerMapper.findLingSan",pd);
    }

    public List<PageData> findQuanBuList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("CustomerMapper.findQuanBuList",pd);
    }


    public void editOwnByCustomerId(PageData pd) throws Exception {
        daoSupport.update("CustomerMapper.editOwnByCustomerId",pd);
    }

    public void editOwnByCustomerIdUp(PageData pd) throws Exception {
        daoSupport.update("CustomerMapper.editOwnByCustomerIdUp",pd);
    }

    public void editOwnByCustomerIdDown(PageData pd) throws Exception {
        daoSupport.update("CustomerMapper.editOwnByCustomerIdDown",pd);
    }
}
