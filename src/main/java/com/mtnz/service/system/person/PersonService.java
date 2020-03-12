package com.mtnz.service.system.person;

import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\4\18 0018.  
 */
@Service
@Resource(name = "personService")
public class PersonService {
    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("PersonMapper.save",pd);
    }

    public void edit(PageData pd) throws Exception {
        daoSupport.update("PersonMapper.edit",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("PersonMapper.delete",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
       return (List<PageData>) daoSupport.findForList("PersonMapper.findList",pd);
    }
}
