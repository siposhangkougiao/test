package com.mtnz.controller.app.supplierbalance;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.app.preorder.model.SelectListBean;
import com.mtnz.controller.app.supplierbalance.model.ReturnBean;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceDetail;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceOwe;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.preorder.PreOrderService;
import com.mtnz.service.system.supplierbalance.SupplierBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@RestController
@RequestMapping(value = "app/supplierBalance")
public class SupplierBalanceController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(SupplierBalanceController.class);

    @Resource
    private SupplierBalanceService supplierBalanceService;

    /**
     * 查询预付款明细列表
     * @param supplierBalanceOwe
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result select(SupplierBalanceOwe supplierBalanceOwe){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(supplierBalanceOwe));
        Result result = new Result(0,"成功");
        try {
            ReturnBean returnBean = supplierBalanceService.select(supplierBalanceOwe);
            result.setData(returnBean);
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
     * 添加明细
     * @param supplierBalanceDetail
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Result insert(@RequestBody SupplierBalanceDetail supplierBalanceDetail){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(supplierBalanceDetail));
        Result result = new Result(0,"成功");
        try {
            int count = supplierBalanceService.insert(supplierBalanceDetail);
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
     * 重置操作
     * @param supplierBalanceOwe
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/updatebegin")
    @Produces(MediaType.APPLICATION_JSON)
    public Result updatebegin(@RequestBody SupplierBalanceOwe supplierBalanceOwe){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(supplierBalanceOwe));
        Result result = new Result(0,"成功");
        try {
            int count = supplierBalanceService.updatebegin(supplierBalanceOwe);
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
     * 查询预付款明细
     * @param id 详情id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectOne(@PathVariable("id") Long id){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(id));
        Result result = new Result(0,"成功");
        try {
            SupplierBalanceOwe supplierBalanceOwe = supplierBalanceService.selectOne(id);
            result.setData(supplierBalanceOwe);
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
     * 撤单
     * @param id 详情id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result reback(@PathVariable("id") Long id,@PathVariable("uid") Long uid){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(id));
        Result result = new Result(0,"成功");
        try {
            int count = supplierBalanceService.reback(id,uid);
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
     * 查询用户在供货商处的预存款
     * @param supplierBalanceOwe
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "selectbalance")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectbalance(SupplierBalanceOwe supplierBalanceOwe){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(supplierBalanceOwe));
        Result result = new Result(0,"成功");
        try {
            SupplierBalanceOwe supplierBalanceOwe1 = supplierBalanceService.selectbalance(supplierBalanceOwe);
            result.setData(supplierBalanceOwe1);
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
