package com.mtnz.service.system.user.impl;

import com.mtnz.controller.app.user.model.LoginSalt;
import com.mtnz.service.system.user.LoginSaltService;
import com.mtnz.sql.system.user.LoginSaltMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginSaltServiceImpl implements LoginSaltService {

    @Resource
    LoginSaltMapper loginSaltMapper;
    @Override
    public void delete(Long storeId) {
        LoginSalt loginSalt = new LoginSalt();
        loginSalt.setStoreId(storeId);
        loginSaltMapper.delete(loginSalt);
    }

    @Override
    public void insert(Long storeId, String salt) {
        LoginSalt loginSalt = new LoginSalt();
        loginSalt.setStoreId(storeId);
        loginSalt.setSalt(salt);
        loginSaltMapper.insertSelective(loginSalt);
    }

    @Override
    public LoginSalt select(String storeId) {
        LoginSalt loginSalt = new LoginSalt();
        loginSalt.setStoreId(Long.valueOf(storeId));

        return loginSaltMapper.selectOne(loginSalt);
    }
}
