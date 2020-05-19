package com.mtnz.controller.app.integralshop;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.customer.model.Customer;
import com.mtnz.controller.app.integralshop.model.IntegralShopDetail;
import com.mtnz.controller.app.integralshop.model.IntegralShopUser;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.customer.CustomerNewService;
import com.mtnz.service.system.integralshop.IntegralShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@RestController
@RequestMapping(value = "app/integralshop")
public class IntegralShopController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(IntegralShopController.class);

    @Resource
    private IntegralShopService integralShopService;

    /**
     * 积分商城首页
     * @param integralShopUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Result index(@RequestBody IntegralShopUser integralShopUser){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(integralShopUser));
        Result result = new Result(0,"成功");
        try {
            IntegralShopUser index= integralShopService.index(integralShopUser);
            result.setData(index);
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

    /**
     * 签到添加积分
     * @param integralShopDetail
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/integral")
    @Produces(MediaType.APPLICATION_JSON)
    public Result insertintegral(@RequestBody IntegralShopDetail integralShopDetail){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(integralShopDetail));
        Result result = new Result(0,"成功");
        try {
            integralShopService.insertintegral(integralShopDetail);
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


    /**
     * 查询积分明细
     * @param integralShopDetail
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/integralDetail")
    @Produces(MediaType.APPLICATION_JSON)
    public Result integralDetail(IntegralShopDetail integralShopDetail){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(integralShopDetail));
        Result result = new Result(0,"成功");
        try {
            PageInfo pageInfo = integralShopService.selectDetail(integralShopDetail);
            result.setData(pageInfo);
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
