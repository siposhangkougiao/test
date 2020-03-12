package com.mtnz.service.system.nongyao;

import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\6\9 0009.  
 */
@Service
@Resource(name = "nongYaoService")
public class NongYaoService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;


    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("NongYaoMapper.findList",pd);
    }

    public void edit(PageData pd) throws Exception {
        daoSupport.update("NongYaoMapper.edit",pd);
    }

    public List<PageData> findLists(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("NongYaoMapper.findLists",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("NongYaoMapper.findById",pd);
    }

    public PageData findlikeNongYao(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("NongYaoMapper.findlikeNongYao",pd);
    }

    public List<PageData> findListsss(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("NongYaoMapper.findListsss",pd);
    }
}
