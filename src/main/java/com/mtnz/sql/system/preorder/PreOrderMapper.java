package com.mtnz.sql.system.preorder;


import com.mtnz.controller.app.preorder.model.PreOrder;
import com.mtnz.controller.base.MyMapper;

import java.util.List;

public interface PreOrderMapper extends MyMapper<PreOrder> {

    List<PreOrder> selectlist(PreOrder preOrder);

    PreOrder selectById(Long id);

}