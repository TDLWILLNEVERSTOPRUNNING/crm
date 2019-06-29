package com.tiandelei.crm.settings.dao;

import com.tiandelei.crm.settings.domin.User;

import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/28
 */
public interface UserDao {
    User login(Map<String, String> map);
}
