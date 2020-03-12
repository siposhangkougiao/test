package com.mtnz.service.system.return_supplier;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\6\25 0025.  
 */
@Service
@Resource(name = "returnSupplierOrderInfoService")
public class ReturnSupplierOrderInfoService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;


    public void save(PageData pd) throws Exception {
        daoSupport.save("ReturnSupplierOrderInfoMapper.save",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("ReturnSupplierOrderInfoMapper.datalistPage",page);
    }

    public PageData findSumMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ReturnSupplierOrderInfoMapper.findSumMoney",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ReturnSupplierOrderInfoMapper.findById",pd);
    }

    public void editRevokes(PageData pd) throws Exception {
        daoSupport.update("ReturnSupplierOrderInfoMapper.editRevokes",pd);
    }

}
