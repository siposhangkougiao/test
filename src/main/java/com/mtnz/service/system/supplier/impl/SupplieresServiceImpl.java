package com.mtnz.service.system.supplier.impl;

import com.mtnz.controller.app.supplier.model.Supplier;
import com.mtnz.controller.app.supplier.model.SupplierOrderPro;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.supplier.SupplieresService;
import com.mtnz.sql.system.supplier.SupplierMapper;
import com.mtnz.sql.system.supplier.SupplierOrderProMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class SupplieresServiceImpl implements SupplieresService {

    @Resource
    SupplierOrderProMapper supplierOrderProMapper;

    @Resource
    SupplierMapper supplierMapper;

    /**
     * 修改详情剩余数量
     * @param supplierOrderPro
     */
    @Override
    public void update(SupplierOrderPro supplierOrderPro) {
        Example example = new Example(SupplierOrderPro.class);
        example.and().andEqualTo("supplierOrderInfoId",supplierOrderPro.getSupplierOrderInfoId());
        example.and().andEqualTo("productId",supplierOrderPro.getProductId());
        SupplierOrderPro bean = supplierOrderProMapper.selectOneByExample(example);
        if(supplierOrderPro.getType()==2){
            if(bean.getNums().compareTo(new BigDecimal(0))<1||bean.getNums().compareTo(supplierOrderPro.getNum())<0){
                throw new ServiceException(-101,"可退数量不足",null);
            }
        }
        SupplierOrderPro upBean = new SupplierOrderPro();
        if(supplierOrderPro.getType()==2){
            upBean.setNums(bean.getNums().subtract(supplierOrderPro.getNum()));
        }else {
            upBean.setNums(bean.getNums().add(supplierOrderPro.getNum()));
        }

        upBean.setSupplierOrderProId(bean.getSupplierOrderProId());
        supplierOrderProMapper.updateByPrimaryKeySelective(upBean);
    }

    /**
     * 查询供货商信息
     * @param supplierbean
     * @return
     */
    @Override
    public Supplier selectSupplier(Supplier supplierbean) {
        return supplierMapper.selectOne(supplierbean);
    }
}
