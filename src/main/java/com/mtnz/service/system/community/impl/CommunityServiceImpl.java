package com.mtnz.service.system.community.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.community.model.Community;
import com.mtnz.controller.app.community.model.CommunityComments;
import com.mtnz.controller.app.user.model.SysAppUser;
import com.mtnz.service.system.community.CommunityService;
import com.mtnz.sql.system.community.CommunityCommentsMapper;
import com.mtnz.sql.system.community.CommunityMapper;
import com.mtnz.sql.system.user.SysAppUserNewMapper;
import com.mtnz.util.QiniuUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CommunityServiceImpl implements CommunityService {

    @Resource
    CommunityMapper communityMapper;

    @Resource
    CommunityCommentsMapper communityCommentsMapper;

    @Resource
    SysAppUserNewMapper sysAppUserNewMapper;

    /**
     * 查询生意圈列表
     * @param community
     * @return
     */
    @Override
    public PageInfo select(Community community) {
        PageHelper.startPage(community.getPageNumber(),community.getPageSize());
        Example examplec = new Example(Community.class);
        examplec.and().andEqualTo("isDelete",0);
        List<Community> list = communityMapper.selectByExample(examplec);
        List<Long> ulist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(!ulist.contains(list.get(i).getUserId())){
                ulist.add(list.get(i).getUserId());
            }
            CommunityComments bean = new CommunityComments();
            bean.setCommunityId(list.get(i).getId());
            bean.setIsDelete(0);
            List<CommunityComments> commentsList = communityCommentsMapper.select(bean);
            for (int j = 0; j < commentsList.size(); j++) {
                if(!ulist.contains(commentsList.get(j).getUserId())){
                    ulist.add(commentsList.get(j).getUserId());
                }
            }
            list.get(i).setCommentsList(commentsList);
            SysAppUser sysAppUser =sysAppUserNewMapper.selectByPrimaryKey(list.get(i).getUserId());
            list.get(i).setReleaseName(sysAppUser.getName());
        }
        Example example = new Example(SysAppUser.class);
        example.and().andIn("uid",ulist);
        List<SysAppUser> sysAppUsers = sysAppUserNewMapper.selectByExample(example);
        Map<Long,String> map = new HashMap();
        for (int i = 0; i < sysAppUsers.size(); i++) {
            map.put(sysAppUsers.get(i).getUid(),sysAppUsers.get(i).getName());
        }
        for (int i = 0; i < list.size(); i++) {
            if(map.containsKey(list.get(i).getUserId())){
                list.get(i).setReleaseName(map.get(list.get(i).getUserId()));
            }
            for (int j = 0; j < list.get(i).getCommentsList().size(); j++) {
                CommunityComments comments = list.get(i).getCommentsList().get(j);
                if(map.containsKey(comments.getUserId())){
                    list.get(i).getCommentsList().get(j).setCommentsName(map.get(comments.getUserId()));
                }
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 发布动态
     * @param community
     */
    @Override
    public void insert(Community community) {
        communityMapper.insertSelective(community);
    }

    /**
     * 修改状态（点赞，删除，修改）
     * @param community
     */
    @Override
    public void update(Community community) {
        //修改点赞数量
        if(community.getPraise()!=null){
            Community community1 = communityMapper.selectByPrimaryKey(community.getId());
            community.setPraise(community.getPraise() + community1.getPraise());
        }
        communityMapper.updateByPrimaryKeySelective(community);
    }

    /**
     * 添加评论
     * @param communityComments
     */
    @Override
    public void insertcomments(CommunityComments communityComments) {
        communityCommentsMapper.insertSelective(communityComments);
    }

    /**
     * 修改评论（点赞，修改，删除）
     * @param communityComments
     */
    @Override
    public void updatecomments(CommunityComments communityComments) {
        if(communityComments.getPraise()!=null){
            CommunityComments comments = communityCommentsMapper.selectByPrimaryKey(communityComments.getId());
            communityComments.setPraise(comments.getPraise()+communityComments.getPraise());
        }
        communityCommentsMapper.updateByPrimaryKeySelective(communityComments);
    }

    /**
     * 七牛获取上传用的token
     * @return
     */
    @Override
    public String getoken() {

        return QiniuUtils.getUpToken();
    }
}
