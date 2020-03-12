package com.mtnz.service.system.repayments;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\6\29 0029.  
 */
@Service
@Resource(name = "repaymentsService")
public class RepaymentsService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("RepaymentsMapper.save",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("RepaymentsMapper.datalistPage",page);
    }

    public PageData findSum(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("RepaymentsMapper.findSum",pd);
    }
}
