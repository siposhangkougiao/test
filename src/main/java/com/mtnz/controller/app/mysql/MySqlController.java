package com.mtnz.controller.app.mysql;


import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.mysql.KuCunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(value = "/app/mysql")
public class MySqlController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(MySqlController.class);

    @Resource
    private KuCunService kuCunService;

    /**
     * 刷新库存不正确的问题
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result save(){
        Result result = new Result(0,"成功");
        try {
            kuCunService.test();
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("系统错误",e);
            result.setCode(-101);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 发送维护通知短信
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/sendMeg")
    @Produces(MediaType.APPLICATION_JSON)
    public Result sendMeg(){
        Result result = new Result(0,"成功");
        try {
            kuCunService.sendMeg();
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("系统错误",e);
            result.setCode(-101);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 刷新数据库图片地址
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/image")
    @Produces(MediaType.APPLICATION_JSON)
    public Result image(){
        Result result = new Result(0,"成功");
        try {
            kuCunService.image();
        }catch (ServiceException e) {
            logger.error("数据操作失败",e);
            result.setCode(e.getExceptionCode());
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("系统错误",e);
            result.setCode(-101);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
