package com.mtnz.service.system.returns;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Created by xxj on 2018\5\30 0030.  
 */
@Service
@Resource(name = "returnOrderProService")
public class ReturnOrderProService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("ReturnOrderProMapper.save",pd);
    }

    public void batchSave(List<PageData> list) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        daoSupport.save("ReturnOrderProMapper.batchSave",map);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ReturnOrderProMapper.findList",pd);
    }

    public List<PageData> findQuanBu(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ReturnOrderProMapper.findQuanBu",pd);
    }
}
