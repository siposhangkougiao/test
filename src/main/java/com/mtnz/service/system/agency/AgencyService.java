package com.mtnz.service.system.agency;

import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\3\21 0021.
 */

@Service("agencyService")
public class AgencyService {

    @Resource(name="daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("AgencyMapper.save",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("AgencyMapper.findList",pd);
    }

    public void editStatus(PageData pd) throws Exception {
        daoSupport.update("AgencyMapper.editStatus",pd);
    }

    public PageData findCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("AgencyMapper.findCount",pd);
    }

    public List<PageData> findMonthList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("AgencyMapper.findMonthList",pd);
    }

    public List<PageData> findUserList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("AgencyMapper.findUserList",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("AgencyMapper.findById",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("AgencyMapper.delete",pd);
    }

    public void edit(PageData pd) throws Exception {
        daoSupport.update("AgencyMapper.edit",pd);
    }

    public List<PageData> findBeforeList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("AgencyMapper.findBeforeList", pd);
    }

    public void editfStatus(PageData pd) throws Exception {
        daoSupport.update("AgencyMapper.editfStatus",pd);
    }

    public List<PageData> findCustomer(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("AgencyMapper.findCustomer",pd);
    }
}
