package com.mtnz.service.system.shorts;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\4\20 0020.  
 */
@Service
@Resource(name = "shortService")
public class ShortService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;


    public void save(PageData pd) throws Exception {
        daoSupport.save("ShortMapper.save",pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("ShortMapper.datalistPage",page);
    }

    public void edit(PageData pd) throws Exception {
        daoSupport.update("ShortMapper.edit",pd);
    }
}
