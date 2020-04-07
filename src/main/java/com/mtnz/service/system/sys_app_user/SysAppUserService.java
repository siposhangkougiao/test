package com.mtnz.service.system.sys_app_user;


import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\3\21 0021.
 */

@Service("sysAppUserService")
public class SysAppUserService {
    @Resource(name="daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("SysAppUserMapper.save",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SysAppUserMapper.findById",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("SysAppUserMapper.datalistPage",page);
    }

    public PageData login(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SysAppUserMapper.login",pd);
    }

    public void editPassword(PageData pd) throws Exception {
        daoSupport.update("SysAppUserMapper.editPassword",pd);
    }
    public PageData findUserName(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SysAppUserMapper.findUserName",pd);
    }

    public PageData findBySId(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("SysAppUserMapper.findBySId",pd);
    }

    public void update(PageData pd) throws Exception {
        daoSupport.update("SysAppUserMapper.update",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("SysAppUserMapper.delete",pd);
    }

    public List<String> findUserList(PageData pd) throws Exception {
        return (List<String>) daoSupport.findForList("SysAppUserMapper.findUserList",pd);
    }

    public void editOpenId(PageData pd) throws Exception {
        daoSupport.update("SysAppUserMapper.editOpenId",pd);
    }


    public List<PageData> findQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("SysAppUserMapper.findQuanBu",pd);
    }

    public List<PageData> findUserByStore(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("SysAppUserMapper.findUserByStore",pd);
    }
    public void editLoginDate(PageData pd) throws Exception {
        daoSupport.update("SysAppUserMapper.editLoginDate",pd);
    }

    public List<PageData> findcostor(PageData pd) throws Exception {

        return (List<PageData>) daoSupport.findForList("SysAppUserMapper.findcostor",pd);
    }

    public List<PageData> findall() throws Exception {
        PageData pd = new PageData();
        return (List<PageData>) daoSupport.findForList("SysAppUserMapper.findall",pd);
    }

    public void editStoreId(PageData pd) throws Exception {
        daoSupport.update("SysAppUserMapper.editStoreId",pd);
    }
}
