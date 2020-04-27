package com.mtnz.service.system.user;


import com.mtnz.controller.app.user.model.LoginSalt;

public interface LoginSaltService {

    /**
     * 删除盐值
     * @param uid
     */
    void delete(Long uid);

    void insert(Long uid, String salt);

    LoginSalt select(String storeId);
}
