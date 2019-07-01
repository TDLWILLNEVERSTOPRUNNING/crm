package com.tiandelei.crm.workbench.service.impl;

import com.tiandelei.crm.settings.dao.UserDao;
import com.tiandelei.crm.settings.domin.User;
import com.tiandelei.crm.utils.SqlSessionUtil;
import com.tiandelei.crm.vo.Paginationvo;
import com.tiandelei.crm.workbench.dao.ActivityDao;
import com.tiandelei.crm.workbench.dao.ActivityRemarkDao;
import com.tiandelei.crm.workbench.domain.Activity;
import com.tiandelei.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/29
 */
public class ActivityServiceImpl implements ActivityService {


    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    private UserDao UserDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public Boolean save(Activity a) {
        boolean flag = true;

        int count = activityDao.save(a);

        //添加一条受影响的只能是一条
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Paginationvo<Activity> pageList(Map<String, Object> map) {


        //取得dataList

        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        //取得total

        int total = activityDao.getTotalByCondition(map);

        //创建一个vo对象，使用对象封装dataList，total

        Paginationvo<Activity> vo = new Paginationvo<Activity>();
        vo.setDataList(dataList);
        vo.setTotal(total);

        //返回vo
        return vo;

    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;

        //查询出需要删除的备注数量
        int count1 = activityRemarkDao.getCountByAids(ids);

        //删除备注
        int count2 = activityRemarkDao.deleteByAids(ids);

        //如果实际删除的数量和需要删除的数量不一致需要返回false
        if (count1 != count2) {

            flag = false;

        }

        //删除市场活动
        int count3 = activityDao.delete(ids);

        if (count3 != ids.length) {

            flag = false;

        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        //取uList
        List<User> uList = UserDao.getUserList();

        //取a
        Activity a = activityDao.getById(id);


        //创建一个map对象
        Map<String,Object> map = new HashMap<>();
        map.put("uList", uList);
        map.put("a",a);


        //返回一个map
        return map;
    }
}
