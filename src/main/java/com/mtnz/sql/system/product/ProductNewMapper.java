package com.mtnz.sql.system.product;


import com.mtnz.controller.app.product.model.Product;
import com.mtnz.controller.base.MyMapper;


public interface ProductNewMapper extends MyMapper<Product> {

    Integer selectOrderCount(Long productId);
}