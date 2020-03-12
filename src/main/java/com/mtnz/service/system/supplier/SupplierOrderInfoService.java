package com.mtnz.service.system.supplier;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\5\29 0029.  
 */
@Service
@Resource(name = "supplierOrderInfoService")
public class SupplierOrderInfoService {

    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("SupplierOrderInfoMapper.save",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierOrderInfoMapper.datalistPage",page);
    }

    public List<PageData> findLikeOrderInfo(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierOrderInfoMapper.findLikeOrderInfo",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findById",pd);
    }


    public PageData findCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findCount",pd);
    }

    public PageData findCounts(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findCounts",pd);
    }

    public PageData findSupplierCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findSupplierCount",pd);
    }

    public PageData findSumMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findSumMoney",pd);
    }

    public void editRevokes(PageData pd) throws Exception {
        daoSupport.update("SupplierOrderInfoMapper.editRevokes",pd);
    }

    public PageData findTotalSumMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findTotalSumMoney",pd);
    }

    public PageData findDateSumMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findDateSumMoney",pd);
    }


    public List<PageData> owelistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierOrderInfoMapper.owelistPage",page);
    }

    public PageData findSumOwe(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderInfoMapper.findSumOwe",pd);
    }

    public List<PageData> AdminlistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierOrderInfoMapper.AdminlistPage",page);
    }


}
