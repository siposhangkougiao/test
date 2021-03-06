package com.mtnz.sql.system.community;


import com.mtnz.controller.app.community.model.Community;
import com.mtnz.controller.base.MyMapper;

import java.util.List;

public interface CommunityMapper extends MyMapper<Community> {

    List<Community> selectByCommunity(Community community);
}