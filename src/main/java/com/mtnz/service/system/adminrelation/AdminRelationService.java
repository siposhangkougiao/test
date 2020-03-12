package com.mtnz.service.system.adminrelation;


import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\6\28 0028.  
 */
@Service
@Resource(name = "adminRelationService")
public class AdminRelationService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;


    private void save(PageData pd) throws Exception {
        daoSupport.save("AdminRelationMapper.save",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("AdminRelationMapper.findList",pd);
    }
}
