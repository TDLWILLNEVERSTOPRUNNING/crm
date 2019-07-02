package com.tiandelei.crm.workbench.web.controller;


import com.tiandelei.crm.settings.domin.User;
import com.tiandelei.crm.settings.service.UserService;
import com.tiandelei.crm.settings.service.impl.UserServiceImpl;
import com.tiandelei.crm.utils.*;
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
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入市场活动模块控制器");

        String Path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(Path)) {

            getUserList(request, response);

        } else if ("/workbench/activity/save.do".equals(Path)) {

            save(request, response);
        } else if ("/workbench/activity/pageList.do".equals(Path)) {

            pageList(request, response);
        } else if ("/workbench/activity/delete.do".equals(Path)) {

            delete(request, response);
        } else if ("/workbench/activity/getUserListAndActivity.do".equals(Path)) {

            getUserListAndActivity(request, response);
        } else if ("/workbench/activity/update.do".equals(Path)) {

            update(request, response);
        } else if ("/workbench/activity/detail.do".equals(Path)) {

            detail(request, response);
        } else if ("/workbench/activity/getRemarkListByAid.do".equals(Path)) {

            getRemarkListByAid(request, response);
        } else if ("/workbench/activity/deleteRemark.do".equals(Path)) {

            deleteRemark(request, response);
        } else if ("/workbench/activity/saveRemark.do".equals(Path)) {

            saveRemark(request, response);
        } else if ("/workbench/activity/updateRemark.do".equals(Path)) {

            updateRemark(request, response);
        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行修改备注操作");

        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditFlag(editFlag);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.updateRemark(ar);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar", ar);

        PrintJson.printJsonObj(response, map);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行添加备注的操作");

        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");

        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setActivityId(activityId);
        ar.setNoteContent(noteContent);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Boolean flag = as.saveRemark(ar);

        Map<String,Object> map = new HashMap<>();

        map.put("success", flag);
        map.put("ar", ar);

        PrintJson.printJsonObj(response, map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("删除备注操作");

        String id = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.deleteRemark(id);

        PrintJson.printJsonFlag(response, flag);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据市场活动id取得备注列表");

        String activityId = request.getParameter("activityId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);

        PrintJson.printJsonObj(response, arList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        System.out.println("跳转到详细信息页操作");

        String id = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = as.detail(id);

        request.setAttribute("a", a);

        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);

    }

    private void update(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动修改操作");

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        //修改时间，当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人 ：当前登录的用户（从session中取user再取name）
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity a = new Activity();

        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setEditBy(editBy);
        a.setEditTime(editTime);


        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Boolean flag = as.update(a);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表和市场活动单条");

        String id = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String,Object> map = as.getUserListAndActivity(id);

        PrintJson.printJsonObj(response, map);

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动删除操作");

        String ids[] = request.getParameterValues("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.delete(ids);

        PrintJson.printJsonFlag(response, true);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动列表（结合分页查询+条件查询）");


        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");

        //第几页
        int pageNo = Integer.valueOf(pageNoStr);
        //取几条数据

        int pageSize = Integer.valueOf(pageSizeStr);
//        int pageSize = Integer.valueOf(pageSizeStr);

        //经过多少条数据取
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        /*
        我管业务要什么？（完全在于前端管我要什么）
        前端管我要total和datalist
        我就得管业务要什么

        业务层帮我拿到了total和datalist之后。可以使用map或者vo的形式为我们做数据的返回
        对于分页+条件查询的操作，除了市场活动之外，其他模块都有这样的操作，所以我们有必要创建
        一个vo类，方便数据保存

        业务层：
        取total
        取atalist
        创建一个vo对象
        把total和datalist保存到vo中
        返回vo
         */
        Paginationvo<Activity> vo = as.pageList(map);

        PrintJson.printJsonObj(response, vo);


    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的添加操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        //创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人 ：当前登录的用户（从session中取user再取name）
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity a = new Activity();

        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);


        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Boolean flag = as.save(a);

        PrintJson.printJsonFlag(response, flag);


    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到查询用户信息列表操作");

        //市场活动发出的请求，理应由市场活动模块来完成，但是在处理业务时，是用户相关的业务所以用userService
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> ulist = us.getUserList();

        //现在接收并处理的Ajax的转换为json串
        PrintJson.printJsonObj(response, ulist);

    }
}
