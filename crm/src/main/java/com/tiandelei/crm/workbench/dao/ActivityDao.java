package com.tiandelei.crm.workbench.dao;

import com.tiandelei.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/29
 */
public interface ActivityDao {
    int save(Activity a);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity getActivityById(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
