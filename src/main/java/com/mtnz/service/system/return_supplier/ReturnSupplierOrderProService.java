package com.mtnz.service.system.return_supplier;

import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\6\25 0025.  
 */
@Service
@Resource(name = "returnSupplierOrderProService")
public class ReturnSupplierOrderProService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("ReturnSupplierOrderProMapper.save",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ReturnSupplierOrderProMapper.findList",pd);
    }

    public List<PageData> findQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ReturnSupplierOrderProMapper.findQuanBu",pd);
    }

}
