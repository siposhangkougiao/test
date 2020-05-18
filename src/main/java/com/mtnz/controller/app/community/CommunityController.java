package com.mtnz.controller.app.community;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.community.model.Community;
import com.mtnz.controller.app.community.model.CommunityComments;
import com.mtnz.controller.app.store.model.StoreLose;
import com.mtnz.controller.base.BaseController;
import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.community.CommunityService;
import com.mtnz.service.system.store.StoreService;
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
@RequestMapping(value = "app/community")
public class CommunityController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Resource
    private CommunityService communityService;

    /**
     *查询生意圈列表
     * @param community
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result select(Community community){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(community));
        Result result = new Result(0,"成功");
        try {
            PageInfo pageInfo = communityService.select(community);
            result.setData(pageInfo);
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
     *发布动态
     * @param community
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Result insert(@RequestBody Community community){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(community));
        Result result = new Result(0,"成功");
        try {
            communityService.insert(community);
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
     *修改状态（点赞，删除，修改）
     * @param community
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Produces(MediaType.APPLICATION_JSON)
    public Result update(@RequestBody Community community){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(community));
        Result result = new Result(0,"成功");
        try {
            communityService.update(community);
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
     * 添加评论
     * @param communityComments
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Result insertcomments(@RequestBody CommunityComments communityComments){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(communityComments));
        Result result = new Result(0,"成功");
        try {
            communityService.insertcomments(communityComments);
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
     * 修改评论（点赞，修改，删除）
     * @param communityComments
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Result updatecomments(@RequestBody CommunityComments communityComments){
        logger.error("接收到的参数：{}",JSONObject.toJSONString(communityComments));
        Result result = new Result(0,"成功");
        try {
            communityService.updatecomments(communityComments);
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
     * 七牛获取上传用的token
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Result getoken(){
        //logger.error("接收到的参数：{}",JSONObject.toJSONString(communityComments));
        Result result = new Result(0,"成功");
        try {
            String token = communityService.getoken();
            result.setData(token);
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
