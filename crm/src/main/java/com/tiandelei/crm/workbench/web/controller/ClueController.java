package com.tiandelei.crm.workbench.web.controller;


import com.tiandelei.crm.settings.domin.User;
import com.tiandelei.crm.settings.service.UserService;
import com.tiandelei.crm.settings.service.impl.UserServiceImpl;
import com.tiandelei.crm.utils.DateTimeUtil;
import com.tiandelei.crm.utils.PrintJson;
import com.tiandelei.crm.utils.ServiceFactory;
import com.tiandelei.crm.utils.UUIDUtil;
import com.tiandelei.crm.vo.Paginationvo;
import com.tiandelei.crm.workbench.domain.Activity;
import com.tiandelei.crm.workbench.domain.ActivityRemark;
import com.tiandelei.crm.workbench.service.ActivityService;
import com.tiandelei.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/28
 */
public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入线索模块控制器");

        String Path = request.getServletPath();

        if ("/workbench/clue/xxx.do".equals(Path)) {

            //xxx(request, response);
        }

    }
}
