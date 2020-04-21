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
public class KuCunController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(KuCunController.class);

    @Resource
    private KuCunService kuCunService;

    /**
     * 添加记事本
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


}
