package com.tiandelei.crm.workbench.dao;

import com.tiandelei.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * Author:田得雷
 * 2019/6/29
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
