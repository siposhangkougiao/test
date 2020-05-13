package com.mtnz.controller.app.preorder;

import com.alibaba.fastjson.JSONObject;
import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.app.preorder.model.SelectListBean;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.preorder.PreOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@RestController
@RequestMapping(value = "app/preorder"/*,produces = "text/html;charset=UTF-8"*/)
public class AppPreOrderController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(AppPreOrderController.class);

    @Resource
    PreOrderService preOrderService;

    /**
     * 添加预支付订单
     * @param preOrder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "save")
    @Produces(MediaType.APPLICATION_JSON)
    public Result save(@RequestBody PreOrder preOrder){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(preOrder));
        Result result = new Result(0,"成功");
        try {
            preOrderService.insert(preOrder);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("系统错误",e);
            result.setCode(-101);
            logger.error("系统错误",e);
        }
        return result;
    }

    /**
     * 预售单列表
     * @param preOrder
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectlist(PreOrder preOrder){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(preOrder));
        Result result = new Result(0,"成功");
        try {
            SelectListBean selectListBean = preOrderService.selectlist(preOrder);
            result.setData(selectListBean);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            result.setMsg("系统错误");
            result.setCode(-101);
            logger.error("系统错误",e);
        }
        return result;
    }

    /**
     * 查询单个预售单
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectById(@PathVariable("id") Long id){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(id));
        Result result = new Result(0,"成功");
        try {
            PreOrder preOrder = preOrderService.selectById(id);
            result.setData(preOrder);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            result.setMsg("系统错误");
            result.setCode(-101);
            logger.error("系统错误",e);
        }
        return result;
    }

    /**
     * 修改预售单
     * @param preOrder
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Produces(MediaType.APPLICATION_JSON)
    public Result update(@RequestBody PreOrder preOrder){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(preOrder));
        Result result = new Result(0,"成功");
        try {
            preOrderService.update(preOrder);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            result.setMsg("系统错误");
            result.setCode(-101);
            logger.error("系统错误",e);
        }
        return result;
    }

}
