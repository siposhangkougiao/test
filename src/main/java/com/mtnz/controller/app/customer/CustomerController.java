package com.mtnz.controller.app.customer;

import com.alibaba.fastjson.JSONObject;
import com.mtnz.controller.app.customer.model.Customer;
import com.mtnz.controller.app.supplierbalance.model.ReturnBean;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceDetail;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceOwe;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.customer.CustomerNewService;
import com.mtnz.service.system.supplierbalance.SupplierBalanceService;
import com.mtnz.sql.system.customer.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@RestController
@RequestMapping(value = "app/newCustomer")
public class CustomerController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Resource
    private CustomerNewService customerNewService;

    /**
     * 添加客户（急速开单）
     * @param customer
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Result insert(@RequestBody Customer customer){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(customer));
        Result result = new Result(0,"成功");
        try {
            Long id = customerNewService.insert(customer);
            result.setData(id);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("系统错误",e);
            result.setCode(-101);
            result.setMsg("系统错误");
        }
        return result;
    }


}
