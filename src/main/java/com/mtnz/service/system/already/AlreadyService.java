package com.mtnz.service.system.already;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\3\21 0021.
 */
@Service("alreadyService")
public class AlreadyService {
    @Resource(name="daoSupport")
    private DaoSupport daoSupport;

    public  void  save(PageData pd) throws Exception {
        daoSupport.save("AlreadyMapper.save",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("AlreadyMapper.datalistPage",page);
    }

    public PageData findSumMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("AlreadyMapper.findSumMoney",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("AlreadyMapper.findById",pd);
    }

    public void editStatus(PageData pd) throws Exception {
        daoSupport.update("AlreadyMapper.editStatus",pd);
    }

    public PageData findOrderNo(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("AlreadyMapper.findOrderNo",pd);
    }
}
