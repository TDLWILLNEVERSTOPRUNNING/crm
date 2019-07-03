package com.tiandelei.crm.workbench.web.controller;



import com.tiandelei.crm.settings.domin.User;
import com.tiandelei.crm.settings.service.UserService;
import com.tiandelei.crm.settings.service.impl.UserServiceImpl;
import com.tiandelei.crm.utils.PrintJson;
import com.tiandelei.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Author:田得雷
 * 2019/6/28
 */
public class ClueController extends HttpServlet {



    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入线索模块控制器");

        String Path = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(Path)) {

            getUserList(request, response);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response, uList);

    }
}
