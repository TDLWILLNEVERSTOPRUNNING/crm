package com.tiandelei.crm.workbench.web.controller;


import com.tiandelei.crm.settings.domin.User;
import com.tiandelei.crm.settings.service.UserService;
import com.tiandelei.crm.settings.service.impl.UserServiceImpl;
import com.tiandelei.crm.utils.DateTimeUtil;
import com.tiandelei.crm.utils.PrintJson;
import com.tiandelei.crm.utils.ServiceFactory;
import com.tiandelei.crm.utils.UUIDUtil;
import com.tiandelei.crm.workbench.domain.Activity;
import com.tiandelei.crm.workbench.domain.Clue;
import com.tiandelei.crm.workbench.domain.Tran;
import com.tiandelei.crm.workbench.service.ActivityService;
import com.tiandelei.crm.workbench.service.ClueService;
import com.tiandelei.crm.workbench.service.impl.ActivityServiceImpl;
import com.tiandelei.crm.workbench.service.impl.ClueServiceImpl;

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

        String path = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(path)) {

            getUserList(request, response);

        } else if ("/workbench/clue/save.do".equals(path)) {

            save(request, response);

        } else if ("/workbench/clue/detail.do".equals(path)) {

            detail(request, response);

        } else if ("/workbench/clue/getActivityListByClueId.do".equals(path)) {

            getActivityListByClueId(request, response);

        } else if ("/workbench/clue/dissociated.do".equals(path)) {

            dissociated(request, response);

        } else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)) {

            getActivityListByNameAndNotByClueId(request, response);

        } else if ("/workbench/clue/bund.do".equals(path)) {

            bund(request, response);

        } else if ("/workbench/clue/getActivityListByName.do".equals(path)) {

            getActivityListByName(request, response);

        } else if ("/workbench/clue/convert.do".equals(path)) {

            convert(request, response);

        }
    }

    //    private void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//
//        System.out.println("执行线索的转换操作");
//
//        String clueId = request.getParameter("clueId");
//
//        String flag = request.getParameter("flag");
//
//        String createBy = ((User)request.getSession().getAttribute("user")).getName();
//
//        Tran t = null;
//
//        //如果标记为a说明需要创建交易
//        if ("a".equals(flag)) {
//
//            t = new Tran();
//
//            //接收交易相关得参数
//            String money = request.getParameter("money");
//            String name = request.getParameter("name");
//            String expectedDate = request.getParameter("expectedDate");
//            String stage = request.getParameter("stage");
//            String activityId = request.getParameter("activityId");
//            String id = UUIDUtil.getUUID();
//            String createTime = DateTimeUtil.getSysTime();
//
//
//            t.setId(id);
//            t.setName(name);
//            t.setMoney(money);
//            t.setActivityId(activityId);
//            t.setStage(stage);
//            t.setExpectedDate(expectedDate);
//            t.setCreateTime(createTime);
//            t.setCreateBy(createBy);
//
//        }
//
//        //t如果为空 对象没有创建出来if没走 不需要创建交易 如果t不为空说明交易需要创建
//        //t传递到业务层如果不为空就需要创建交易，为空就不创建
//
//        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
//
//        //boolean flog = cs.convert(clueId,t,createBy);
//
//        //传统请求 转发   如果转换成功
//        //if (flog) {
//            //如果转换成功，重定向到列表页
//            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
//
//        //如果转换失败
//       // } else {
//            //如果转换失败，重定向到错误页
//
//
//        }
//
//    }
    private void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("执行线索的转换操作");

        String clueId = request.getParameter("clueId");

        String flag = request.getParameter("flag");

        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Tran t = null;

        //如果标记为a，说明需要创建交易
        if ("a".equals(flag)) {

            t = new Tran();

            //接收交易相关参数
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();


            t.setId(id);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);


        }

        //t-----------------------

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        /*

            需要为业务层提供哪些参数：

            clueId
            t


         */
        boolean flag1 = cs.convert(clueId, t, createBy);

        //如果转换成功
        if (flag1) {

            //重定向到列表页
            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");


            //如果转换失败
        } else {

            //提供一个错误页

        }


    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("查询市场活动列表（根据姓名模糊查）");

        String aname = request.getParameter("aname");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByName(aname);

        PrintJson.printJsonObj(response, aList);

    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行线索关联的操作");

        String cid = request.getParameter("cid");

        String aids[] = request.getParameterValues("aid");

        //System.out.println("====================================cid:"+cid);
        //System.out.println("====================================aids:"+aids);

//        if(aids!=null && aids.length>0){
//
//            for(String aid:aids){
//
//                System.out.println("-----------------------"+aid);
//
//            }
//
//
//
//        }

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.bund(cid, aids);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("查询市场活动列表根据名称模糊查，排除掉已经关连过的，指定的市场活动");

        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");

        Map<String, String> map = new HashMap<>();
        map.put("aname", aname);
        map.put("clueId", clueId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);

        PrintJson.printJsonObj(response, aList);

    }

    private void dissociated(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("解除关联关系");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.dissociated(id);

        PrintJson.printJsonFlag(response, flag);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据线索的ID取得关联的市场活动列表");

        String clueId = request.getParameter("clueId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByClueId(clueId);

        PrintJson.printJsonObj(response, aList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("跳转到详细信息页");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue c = cs.detail(id);

        request.setAttribute("c", c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行线索添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();

        c.setId(id);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setCompany(company);
        c.setJob(job);
        c.setEmail(email);
        c.setPhone(phone);
        c.setWebsite(website);
        c.setMphone(mphone);
        c.setState(state);
        c.setSource(source);
        c.setCreateBy(createBy);
        c.setCreateTime(createTime);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);
        c.setAddress(address);


        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.save(c);

        PrintJson.printJsonFlag(response, flag);


    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response, uList);

    }
}
