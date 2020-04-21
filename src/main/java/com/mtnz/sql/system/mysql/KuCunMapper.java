package com.mtnz.sql.system.mysql;


import com.mtnz.controller.app.mysql.model.GetBean;
import com.mtnz.controller.app.mysql.model.KuCun;
import com.mtnz.controller.app.mysql.model.Product;
import com.mtnz.controller.base.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KuCunMapper extends MyMapper<KuCun> {

    Integer selectSum(Long productId);

    void updatekucun(KuCun kuCun);

    KuCun selectdesc(Long productId);

    void updatelist(@Param("list") List<KuCun> updatelist);

    void updateling(@Param("list")List<KuCun> updateling);

    List<GetBean> selectgetbean(@Param("list")List<Product> list);
}