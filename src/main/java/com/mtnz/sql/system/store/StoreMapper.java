package com.mtnz.sql.system.store;


import com.mtnz.controller.app.store.model.Store;
import com.mtnz.controller.app.store.model.StoreUser;
import com.mtnz.controller.base.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper extends MyMapper<Store> {

    Integer selectSumNumber(@Param("list") List<Long> list);
}