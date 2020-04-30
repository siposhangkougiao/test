package com.mtnz.sql.system.mysql;


import com.mtnz.controller.app.mysql.model.Product;
import com.mtnz.controller.base.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductMapper extends MyMapper<Product> {

    void updatelist(@Param("list") List<Product> insertlist);

    Product selectlikeimg(Product bean);
}