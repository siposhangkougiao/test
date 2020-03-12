package com.mtnz.service.system.product;

import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\3\23 0023.  
 */
@Service
@Resource(name = "productImgService")
public class ProductImgService {

    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("ProductImgMapper.save",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductImgMapper.findList",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("ProductImgMapper.delete",pd);
    }
}
