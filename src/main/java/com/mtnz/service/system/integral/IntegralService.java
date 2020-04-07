package com.mtnz.service.system.integral;


import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("integralService")
public class IntegralService {

    @Resource(name="daoSupport")
    private DaoSupport daoSupport;

    public List<PageData> findUserIntegral(PageData pd) throws Exception {

        return (List<PageData>) daoSupport.findForList("IntegralMapper.findUserIntegral",pd);
    }

    public List<PageData> findUserIntegrallist(PageData pd) throws Exception {

        return (List<PageData>) daoSupport.findForList("IntegralDetailMapper.findUserIntegrallist",pd);
    }

    public PageData findIntegralDetail(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("IntegralDetailMapper.findIntegralDetail",pd);
    }

    public PageData findIntegralSetup(PageData pd) throws Exception {

        return (PageData) daoSupport.findForObject("IntegralSetupMapper.findIntegralSetup",pd);
    }

    public void deleteIntegralSetup(PageData pd) throws Exception {
        daoSupport.delete("IntegralSetupMapper.deleteIntegralSetup",pd);
    }

    public void saveIntegralSetup(PageData pd) throws Exception {
        daoSupport.save("IntegralSetupMapper.saveIntegralSetup",pd);
    }

    public void saveIntegralDetail(PageData pd) throws Exception {
        daoSupport.save("IntegralDetailMapper.saveIntegralDetail",pd);
    }

    /**
     * 根据用户id查询是否有记录
     * @param pd
     * @return
     * @throws Exception
     */
    public Integer findIntegralUserById(PageData pd) throws Exception {
        return (Integer) daoSupport.findForObject("IntegralMapper.findIntegralUserById",pd);
    }

    public void saveIntegralUser(PageData pd) throws Exception {
        daoSupport.save("IntegralMapper.saveIntegralUser",pd);
    }

    /**
     * 这个是加积分的
     * @param pd
     * @throws Exception
     */
    public void editIntegral(PageData pd) throws Exception {
        daoSupport.update("IntegralMapper.editIntegral",pd);
    }

    public PageData findIntegralDetailById(Long id) throws Exception {
        PageData pd = new PageData();
        pd.put("id",id);
        return (PageData) daoSupport.findForObject("IntegralDetailMapper.findIntegralDetailById",pd);
    }

    public void editIntegralDetailById(PageData pd) throws Exception {

        daoSupport.update("IntegralDetailMapper.editIntegralDetailById",pd);
    }

    /**
     * 这个是减积分的
     */
    public void editIntegralUser(PageData pd) throws Exception {
        daoSupport.update("IntegralMapper.editIntegralUser",pd);
    }

    /**
     * 查询订单是否有积分产生
     * @param pd
     * @return
     */
    public Integer findIntegralDetailCountByOrderId(PageData pd) throws Exception {
        return (Integer) daoSupport.findForObject("IntegralDetailMapper.findIntegralDetailCountByOrderId",pd);
    }

    /**
     * 通过订单id撤销积分
     * @param pd
     */
    public void editIntegralDetailByOrderId(PageData pd) throws Exception {
        daoSupport.update("IntegralDetailMapper.editIntegralDetailByOrderId",pd);
    }

    /**
     * 通过订单id 查询积分详情
     * @param pd
     * @return
     */
    public PageData findIntegralDetailByOrderId(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("IntegralDetailMapper.findIntegralDetailByOrderId",pd);
    }

    /**
     * 查询客户积分
     * @param pd
     * @return
     */
    public PageData findUserIntegralByUserid(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("IntegralMapper.findUserIntegralByUserid",pd);
    }

    public void saveIntegralReturn(PageData pd) throws Exception {
        daoSupport.save("IntegralDetailMapper.saveIntegralReturn",pd);
    }

    public PageData findIntegralDetailByOrderIdAndType(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("IntegralDetailMapper.findIntegralDetailByOrderIdAndType",pd);
    }

    public Integer findIntegralDetailsCountByOrderId(PageData pd) throws Exception {
        return (Integer) daoSupport.findForObject("IntegralDetailMapper.findIntegralDetailsCountByOrderId",pd);
    }

    public void editIntegralDetailPassById(PageData pd) throws Exception {
        daoSupport.update("IntegralDetailMapper.editIntegralDetailPassById",pd);
    }

    public List<PageData> findIntegralDetailListByOrderId(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("IntegralDetailMapper.findIntegralDetailListByOrderId",pd);
    }

    public PageData findIntegralDetailReturnByOrderId(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("IntegralDetailMapper.findIntegralDetailReturnByOrderId",pd);
    }
}
