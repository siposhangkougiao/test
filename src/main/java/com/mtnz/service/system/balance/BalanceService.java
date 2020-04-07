package com.mtnz.service.system.balance;


import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("balanceService")
public class BalanceService {

    @Resource(name="daoSupport")
    private DaoSupport daoSupport;


    public PageData findUserbalanceByUserId(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("BalanceUserMapper.findUserbalanceByUserId",pd);
    }

    public void saveBalance(PageData pd) throws Exception {
        daoSupport.save("BalanceUserMapper.saveBalance",pd);
    }

    public void saveBalanceDetail(PageData pd) throws Exception {
        daoSupport.save("BalanceDetailMapper.saveBalanceDetail",pd);
    }

    /**
     * 增加余额
     * @param pd
     */
    public void editBalanceByUserIdUp(PageData pd) throws Exception {
        daoSupport.update("BalanceUserMapper.editBalanceByUserIdUp",pd);
    }

    public PageData findBalanceDetailById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("BalanceDetailMapper.findBalanceDetailById",pd);
    }

    public List<PageData> findUserbalanceDetailByUserId(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("BalanceDetailMapper.findUserbalanceDetailByUserId",pd);
    }

    public void editBalanceByUserId(PageData pd) throws Exception {
        daoSupport.update("BalanceDetailMapper.editBalanceByUserId",pd);
    }

    public void editBalanceByUserIdDown(PageData pd) throws Exception {
        daoSupport.update("BalanceUserMapper.editBalanceByUserIdDown",pd);
    }

    public void saveBalanceDetailSaveOrder(PageData pd) throws Exception {
        daoSupport.save("BalanceDetailMapper.saveBalanceDetailSaveOrder",pd);
    }

    public PageData findBalanceDetailByOrderId(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("BalanceDetailMapper.findBalanceDetailByOrderId",pd);
    }

    public void editBalanceDetailIsPassByOrderOId(PageData pd) throws Exception {
        daoSupport.update("BalanceDetailMapper.editBalanceDetailIsPassByOrderOId",pd);
    }

    public void saveBalanceReturn(PageData pd) throws Exception {
        daoSupport.save("BalanceReturnMapper.saveBalanceReturn",pd);
    }

    public List<PageData> findBalanceRetuenByOrderId(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("BalanceReturnMapper.findBalanceRetuenByOrderId",pd);
    }
}
