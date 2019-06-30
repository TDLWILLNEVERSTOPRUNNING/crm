package com.tiandelei.crm.workbench.service.impl;

import com.tiandelei.crm.utils.SqlSessionUtil;
import com.tiandelei.crm.workbench.dao.ActivityDao;
import com.tiandelei.crm.workbench.domain.Activity;
import com.tiandelei.crm.workbench.service.ActivityService;

/**
 * Author:田得雷
 * 2019/6/29
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public Boolean save(Activity a) {
        boolean flag = true;

        int count = activityDao.save(a);

        //添加一条受影响的只能是一条
        if (count != 1){
            flag = false;
        }

        return flag;
    }
}
