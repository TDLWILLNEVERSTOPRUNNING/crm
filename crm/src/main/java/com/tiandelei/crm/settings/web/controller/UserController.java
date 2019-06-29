package com.tiandelei.crm.settings.web.controller;

import com.tiandelei.crm.exception.LoginException;
import com.tiandelei.crm.settings.domin.User;
import com.tiandelei.crm.settings.service.UserService;
import com.tiandelei.crm.settings.service.impl.UserServiceImpl;
import com.tiandelei.crm.utils.MD5Util;
import com.tiandelei.crm.utils.PrintJson;
import com.tiandelei.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/28
 */
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入用户模块控制器");

        String Path = request.getServletPath();

        if ("/settings/user/login.do".equals(Path)){

            login(request,response);

        }else if ("/settings/user/xxx.do".equals(Path)){



        }
    }


    //登陆验证
    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到登陆验证的操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);

        //获取浏览器的ip
        String ip = request.getRemoteAddr();

        //传李四
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        //login()传参返回User对象
        User user = null;
        try {


            user = us.login(loginAct,loginPwd,ip);

            //r如果程序能顺利的走到该行，说明上面的业务层没有抛异常，表示登录成功
            //把User保存到session域对象中
            request.getSession().setAttribute("user", user);

            //为前端提供信息
            //{"success":true}

            PrintJson.printJsonFlag(response, true);

        } catch (LoginException e) {
            e.printStackTrace();

            //进入到cath 说明业务层为我们抛出了异常，证明登录失败
            //取得异常信息值
            String msg = e.getMessage();

            //为前端提供信息
            //{"success':false/false,"msg":?} 可以使用map，如果需求返回json复用率高 也可以使用VO。没必要在创建一个vo类 只有登陆验证是才传。。。
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);

            PrintJson.printJsonObj(response, map);

        }


    }
}
