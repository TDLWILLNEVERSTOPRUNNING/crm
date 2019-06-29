package com.tiandelei.crm.settings.service.impl;

import com.tiandelei.crm.exception.LoginException;
import com.tiandelei.crm.settings.dao.UserDao;
import com.tiandelei.crm.settings.domin.User;
import com.tiandelei.crm.settings.service.UserService;
import com.tiandelei.crm.utils.DateTimeUtil;
import com.tiandelei.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/28
 */
public class UserServiceImpl implements UserService {

    //调Dao层

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        //把loginAct，loginPwd保存到map 中
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userDao.login(map);

        if (user==null){
            //进入到if块说明账号密码不正确 自定义异常
            throw new LoginException("账号密码不正确");
        }

        //如果程序执行到了这一行说明if没走，说明user不为空，说明user账号密码正确
        //验证其他信息
        //失效时间
        String expireTime = user.getExpireTime();
        if (expireTime.compareTo(DateTimeUtil.getSysTime())<0){

            throw new LoginException("账号已失效");

        }

        //验证锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){

            throw new LoginException("账号已锁定");

        }

        //验证ip
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){

            throw new LoginException("IP地址失效");

        }

        //如果程序能够走到该行，说明没有欸有抛出任何异常，说名登录成功返回user对象
        return user;
    }
}
