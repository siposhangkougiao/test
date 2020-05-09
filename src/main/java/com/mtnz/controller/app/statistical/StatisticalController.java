package com.mtnz.controller.app.statistical;

import com.alibaba.fastjson.JSONObject;
import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.app.statistical.model.ReturnBean;
import com.mtnz.controller.app.statistical.model.ReturnSelectSale;
import com.mtnz.controller.app.statistical.model.ReturnStore;
import com.mtnz.controller.app.statistical.model.StBean;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.statistical.StatisticalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@RestController
@RequestMapping(value = "app/statistical")
public class StatisticalController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(StatisticalController.class);

    @Resource
    private StatisticalService statisticalService;

    /**
     * 查询日注册量、开单量、活跃量
     * @param stBean
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result select(StBean stBean){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(stBean));
        Result result = new Result(0,"成功");
        try {
            ReturnBean re =statisticalService.select(stBean);
            result.setData(re);
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
     * 查询所有店铺的销售额利润等信息
     * @param stBean
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "sale")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectSale(StBean stBean){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(stBean));
        Result result = new Result(0,"成功");
        try {
            ReturnSelectSale re =statisticalService.selectSale(stBean);
            result.setData(re);
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
