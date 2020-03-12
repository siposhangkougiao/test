package com.mtnz.service.system.product;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\4\2 0002.  
 */
@Service
@Resource(name = "weProductService")
public class WeProductService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("WeProductMapper.save",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("WeProductMapper.findById",pd);
    }

    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("WeProductMapper.datalistPage",page);
    }

    public void updateProduct(PageData pd) throws Exception {
        daoSupport.update("WeProductMapper.updateProduct",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("WeProductMapper.delete",pd);
    }

    public void editImg(PageData pd) throws Exception {
        daoSupport.update("WeProductMapper.editImg",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("WeProductMapper.findList",pd);
    }
}
