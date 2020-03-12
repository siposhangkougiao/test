package com.mtnz.service.system.returns;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\5\30 0030.  
 */
@Service
@Resource(name = "returnOrderInfoService")
public class ReturnOrderInfoService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("ReturnOrderInfoMapper.save",pd);
    }

    public PageData findDateSumMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ReturnOrderInfoMapper.findDateSumMoney",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("ReturnOrderInfoMapper.datalistPage",page);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ReturnOrderInfoMapper.findById",pd);
    }


    public List<PageData> findLikeOrderInfo(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ReturnOrderInfoMapper.findLikeOrderInfo",pd);
    }

    public PageData findSumMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ReturnOrderInfoMapper.findSumMoney",pd);

    }

    public PageData findSumMoneys(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ReturnOrderInfoMapper.findSumMoneys",pd);
    }

    public void editRevokes(PageData pd) throws Exception {
        daoSupport.update("ReturnOrderInfoMapper.editRevokes",pd);
    }

}
