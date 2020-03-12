package com.mtnz.service.system.supplier;

import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\6\4 0004.  
 */
@Service
@Resource(name = "relationService")
public class RelationService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("RelationMapper.save",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("RelationMapper.findList",pd);
    }

    public PageData findCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("RelationMapper.findCount",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("RelationMapper.delete",pd);
    }
}
