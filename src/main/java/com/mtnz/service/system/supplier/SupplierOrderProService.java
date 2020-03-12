package com.mtnz.service.system.supplier;

import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\5\29 0029.  
 */
@Service
@Resource(name = "supplierOrderProService")
public class SupplierOrderProService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("SupplierOrderProMapper.save",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierOrderProMapper.findList",pd);
    }

    public void batchSave(List<PageData> list,String order_info_id) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        map.put("supplier_order_info_id",order_info_id);
        daoSupport.save("SupplierOrderProMapper.batchSave",map);
    }

    public PageData findSumProduct(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderProMapper.findSumProduct",pd);
    }

    public PageData findSumPro(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SupplierOrderProMapper.findSumPro",pd);
    }

    public List<PageData> findQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierOrderProMapper.findQuanBu",pd);
    }

    public List<PageData> findAdminQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("SupplierOrderProMapper.findAdminQuanBu",pd);
    }
}
