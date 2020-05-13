package com.mtnz.controller.app.product;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.app.preorder.model.SelectListBean;
import com.mtnz.controller.app.product.model.Product;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.preorder.PreOrderService;
import com.mtnz.service.system.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@RestController
@RequestMapping(value = "app/newproduct")
public class ProductNewController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(ProductNewController.class);

    @Resource
    ProductService productService;

    /**
     * 开单查询商品列表
     * @param product
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result select(Product product){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(product));
        Result result = new Result(0,"成功");
        try {
            PageInfo list = productService.selectList(product);
            result.setData(list);
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


}
