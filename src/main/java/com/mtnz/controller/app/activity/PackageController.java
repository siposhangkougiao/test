package com.mtnz.controller.app.activity;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.activity.model.ActivityPackage;
import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.app.preorder.model.SelectListBean;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.activity.PackageService;
import com.mtnz.service.system.preorder.PreOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@RestController
@RequestMapping(value = "app/package")
public class PackageController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(PackageController.class);

    @Resource
    private PackageService packageService;

    /**
     * 添加套餐
     * @param activityPackage
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Result insert(@RequestBody ActivityPackage activityPackage){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(activityPackage));
        Result result = new Result(0,"成功");
        try {
            packageService.insert(activityPackage);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            result.setMsg("系统错误");
            result.setCode(-101);
            logger.error("系统错误");
        }
        return result;
    }

    /**
     * 修改套餐(可以作为删除套餐)
     * @param activityPackage
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Produces(MediaType.APPLICATION_JSON)
    public Result update(@RequestBody ActivityPackage activityPackage){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(activityPackage));
        Result result = new Result(0,"成功");
        try {
            packageService.update(activityPackage);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            result.setMsg("系统错误");
            result.setCode(-101);
            logger.error("系统错误");
        }
        return result;
    }

    /**
     * 查询列表
     * @param activityPackage
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result select(ActivityPackage activityPackage){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(activityPackage));
        Result result = new Result(0,"成功");
        try {
            PageInfo pageInfo = packageService.select(activityPackage);
            result.setData(pageInfo);
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            result.setMsg("系统错误");
            result.setCode(-101);
            logger.error("系统错误");
        }
        return result;
    }

}
