package com.mtnz.service.system.store;


import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\3\21 0021.
 */

@Service("storeService")
public class StoreService {
    @Resource(name="daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("StoreMapper.save",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("StoreMapper.findById",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("StoreMapper.datalistPage",page);
    }

    public void updateName(PageData pd) throws Exception {
        daoSupport.update("StoreMapper.updateName",pd);
    }

    public void updateNumber(PageData pd) throws Exception {
        daoSupport.update("StoreMapper.updateNumber",pd);
    }

    public void updateJiaNumber(PageData pd) throws Exception {
        daoSupport.update("StoreMapper.updateJiaNumber",pd);
    }

    public void editQrCode(PageData pd) throws Exception {
        daoSupport.update("StoreMapper.editQrCode",pd);
    }

    public void editBusiness(PageData pd) throws Exception {
        daoSupport.update("StoreMapper.editBusiness",pd);
    }

    public List<PageData> findQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("StoreMapper.findQuanBu",pd);
    }

    public void editPhoneTow(PageData pd) throws Exception {
        daoSupport.update("StoreMapper.editPhoneTow",pd);
    }
}
