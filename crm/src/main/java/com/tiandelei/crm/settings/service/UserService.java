package com.tiandelei.crm.settings.service;

import com.tiandelei.crm.exception.LoginException;
import com.tiandelei.crm.settings.domin.User;

import java.util.List;

/**
 * Author:田得雷
 * 2019/6/28
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
