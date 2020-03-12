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
@Resource(name = "supplierService")
public class SupplierService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;


    public void save(PageData pd) throws Exception {
        daoSupport.save("SupplierMapper.save",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierMapper.datalistPage",page);
    }

    public List<PageData> findLikeSupplier(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierMapper.findLikeSupplier",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierMapper.findById",pd);
    }

    public void edit(PageData pd) throws Exception {
        daoSupport.update("SupplierMapper.edit",pd);
    }

    public void editStatus(PageData pd) throws Exception {
        daoSupport.update("SupplierMapper.editStatus",pd);
    }

    public void editOwe(PageData pd) throws Exception {
        daoSupport.update("SupplierMapper.editOwe",pd);
    }

    public List<PageData> owelistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierMapper.owelistPage",page);
    }



}
