package com.mtnz.controller.app.notepad;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.notepad.model.Notepad;
import com.mtnz.controller.app.notepad.model.NotepadType;
import com.mtnz.controller.base.BaseController;

import com.mtnz.controller.base.Result;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.notepad.NotepadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping(value = "/app/note")
public class AppNoteController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(AppNoteController.class);

    @Resource
    private NotepadService notepadService;

    /**
     * 添加记事本
     * @param notepad
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Result save(@RequestBody Notepad notepad){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(notepad));
        Result result = new Result(0,"成功");
        try {
            notepadService.save(notepad);
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
     * 查询列表（有分页）
     * @param notepad
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectlist(Notepad notepad){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(notepad));
        Result result = new Result(0,"成功");
        try {
            PageInfo<Notepad> list  = notepadService.selectlist(notepad);
            result.setData(list);
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
     * 查询列表（无分页）
     * @param notepad
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/nopage")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectlistnopage(Notepad notepad){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(notepad));
        Result result = new Result(0,"成功");
        try {
            List<Notepad> list  = notepadService.selectlistnopage(notepad);
            result.setData(list);
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
     * 查询单个详情
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selectone(@PathVariable("id") Long id){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(id));
        Result result = new Result(0,"成功");
        try {
            Notepad notepad = notepadService.selectone(id);
            result.setData(notepad);
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
     * 查询分类列表
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "selecttype/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selecttype(@PathVariable("userId") Long userId){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(userId));
        Result result = new Result(0,"成功");
        try {
            List<NotepadType> notepadTypes = notepadService.selecttype(userId);
            result.setData(notepadTypes);
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
     * 修改记事本
     * @param notepad
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/detail")
    @Produces(MediaType.APPLICATION_JSON)
    public Result updateDetail(@RequestBody Notepad notepad){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(notepad));
        Result result = new Result(0,"成功");
        try {
            Integer count = notepadService.updateDetail(notepad);
            result.setData(count);
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
     * 修改类型
     * @param notepadType
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/type")
    @Produces(MediaType.APPLICATION_JSON)
    public Result updateType(@RequestBody List<NotepadType> notepadType){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(notepadType));
        Result result = new Result(0,"成功");
        try {
            Integer count = notepadService.updateType(notepadType);
            result.setData(count);
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
     * 查询类型列表
     * @param notepadType
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/selecttypelist")
    @Produces(MediaType.APPLICATION_JSON)
    public Result selecttypelist(NotepadType notepadType){
        logger.error("接收到的参数：{}", JSONObject.toJSONString(notepadType));
        Result result = new Result(0,"成功");
        try {
            List<NotepadType> list = notepadService.selecttypelist(notepadType);
            result.setData(list);
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
