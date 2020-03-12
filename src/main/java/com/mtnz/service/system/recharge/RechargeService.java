package com.mtnz.service.system.recharge;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\4\19 0019.  
 */
@Service
@Resource(name = "rechargeService")
public class RechargeService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("RechargeMapper.save",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("RechargeMapper.datalistPage",page);
    }
}
