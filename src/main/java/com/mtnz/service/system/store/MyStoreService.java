package com.mtnz.service.system.store;


import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("myStoreService")
public class MyStoreService {

    @Resource(name="daoSupport")
    private DaoSupport daoSupport;


    public PageData findyzm(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ValidationYzmMapper.findyzm",pd);
    }

    public void saveyzm(PageData pd) throws Exception {
        daoSupport.save("ValidationYzmMapper.saveyzm",pd);
    }

    public PageData findyzmBycode(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ValidationYzmMapper.findyzmBycode",pd);
    }

    public void editCode(PageData pd) throws Exception {
        daoSupport.update("ValidationYzmMapper.editCode",pd);
    }

    public void editStore(PageData pd) throws Exception {
        daoSupport.update("ValidationYzmMapper.editStore",pd);
    }

    public List<PageData> findRegisterList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ValidationYzmMapper.findRegisterList",pd);
    }

    public PageData selectResult(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ValidationYzmMapper.selectResult",pd);
    }
}
