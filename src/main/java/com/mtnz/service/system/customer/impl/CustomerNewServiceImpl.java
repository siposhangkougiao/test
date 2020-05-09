package com.mtnz.service.system.customer.impl;

import com.mtnz.controller.app.customer.model.Customer;
import com.mtnz.service.system.customer.CustomerNewService;
import com.mtnz.sql.system.customer.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class CustomerNewServiceImpl implements CustomerNewService {

    @Resource
    private CustomerMapper customerMapper;

    /**
     * 添加客户（急速开单）
     * @param customer
     * @return
     */
    @Override
    public Long insert(Customer customer) {
        Customer in = new Customer();
        in.setStoreId(customer.getStoreId());
        in.setAddress("");
        in.setCrop("");
        in.setArea("");
        in.setName(customer.getName());
        in.setPhone(customer.getPhone());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        in.setInputDate(sdf.format(new Date()));
        in.setBillingDate(sdf.format(new Date()));
        in.setOwe("0");
        in.setStatus("0");
        in.setUid(customer.getUid());
        customerMapper.insertSelective(in);
        return in.getCustomerId();
    }
}
