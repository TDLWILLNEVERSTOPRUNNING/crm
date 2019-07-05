package com.tiandelei.crm.workbench.service;

import com.tiandelei.crm.vo.Paginationvo;
import com.tiandelei.crm.workbench.domain.Activity;
import com.tiandelei.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/29
 */
public interface ActivityService {
    Boolean save(Activity a);

    Paginationvo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    Boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id);

    Boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    List<Activity> getActivityListByClueId(String clueId);


    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);


    List<Activity> getActivityListByName(String aname);
}
