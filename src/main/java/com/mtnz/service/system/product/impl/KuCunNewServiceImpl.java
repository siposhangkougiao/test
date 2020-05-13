package com.mtnz.service.system.product.impl;

import com.mtnz.controller.app.product.model.KuCun;
import com.mtnz.service.system.product.KuCunNewService;
import com.mtnz.sql.system.product.KuCunNewMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class KuCunNewServiceImpl implements KuCunNewService {

    @Resource
    KuCunNewMapper kuCunNewMapper;

    /**
     * 查询进货库存信息
     * @param kuCun
     * @return
     */
    @Override
    public KuCun selectKuCun(KuCun kuCun) {

        return kuCunNewMapper.selectOne(kuCun);
    }

    /**
     * 修改现有数量
     * @param upkucun
     */
    @Override
    public void updateNums(KuCun upkucun) {

        kuCunNewMapper.updateByPrimaryKeySelective(upkucun);
    }
}
